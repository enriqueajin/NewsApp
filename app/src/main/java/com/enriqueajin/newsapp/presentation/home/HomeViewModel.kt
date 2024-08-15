package com.enriqueajin.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    var localState = MutableStateFlow(HomeLocalState())

    private val latestArticles: Flow<List<Article>> = getNewsByCategoryUseCase.getArticlesByCategory()
    private val articlesByKeyword: Flow<List<Article>> =
        localState.flatMapLatest { state ->
            getNewsByKeywordUseCase.getArticlesByKeyword(keyword = state.keyword)
        }

    val uiState: StateFlow<HomeUiState> = combine(
        localState,
        latestArticles,
        articlesByKeyword
    ) { local, latestArticles, articlesByKeyword ->
        HomeUiState.Success(
            latestArticles = latestArticles,
            articlesByKeyword = articlesByKeyword,
            category = local.category,
            keyword = local.keyword
        )
    }.catch {
        HomeUiState.Error(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    // Handled separately since works upon the user interaction with categories
    val newsByCategory: StateFlow<PagingData<Article>> =
        localState.flatMapLatest { state ->
            getNewsByCategoryUseCase(category = state.category).cachedIn(viewModelScope)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PagingData.empty()
        )
}