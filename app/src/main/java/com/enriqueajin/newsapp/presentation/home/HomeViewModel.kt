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

    var localState = MutableStateFlow(HomeLocalUiState())

    private val latestArticles: Flow<List<Article>> = getNewsByCategoryUseCase.getArticlesByCategory()
    private val articlesByKeyword: Flow<List<Article>> = getNewsByKeywordUseCase.getArticlesByKeyword(keyword = localState.value.keyword)

    val uiState: StateFlow<HomeUiState> = combine(
        localState,
        latestArticles,
        articlesByKeyword
    ) { state, latestArticles, articlesByKeyword ->
        HomeUiState.Success(
            latestArticles = latestArticles,
            articlesByKeyword = articlesByKeyword,
            category = state.category,
            keyword = state.keyword
        )
    }.catch {
        HomeUiState.Error(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    val newsByCategory: StateFlow<PagingData<Article>> = localState.flatMapLatest { state ->
        getNewsByCategoryUseCase(category = state.category).cachedIn(viewModelScope)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = PagingData.empty()
    )

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.UpdateCategory -> updateCategory(event.category)
            is HomeEvent.UpdateCategoriesScrollPosition -> setScrollPosition(event.position)
        }
    }

    private fun updateCategory(category: String) {
        localState.value = localState.value.copy(category = category)
    }

    private fun setScrollPosition(position: Int) {
        localState.value = localState.value.copy(categoriesScrollPosition = position)
    }
}