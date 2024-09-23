package com.enriqueajin.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.domain.use_case.GetPagedNewsByCategoryUseCase
import com.enriqueajin.newsapp.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getPagedNewsByCategoryUseCase: GetPagedNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCategoryChange -> onCategoryChange(event.category)
            is HomeEvent.OnCategoryScrollPositionChange -> onCategoryScrollPositionChange(event.scrollPosition)
            HomeEvent.OnRetry -> retryFetchingArticles()
        }
    }

    var localState = MutableStateFlow(HomeLocalState())

    // StateFlow that increases by 1 to trigger the retry
    private val retryTrigger = MutableStateFlow(0)

    private val latestArticles: Flow<Result<List<Article>, DataError.Network>> = getNewsByCategoryUseCase()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val articlesByKeyword: Flow<Result<List<Article>, DataError.Network>> =
        localState.flatMapLatest { state ->
            getNewsByKeywordUseCase(keyword = state.keyword)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> =
        retryTrigger.flatMapLatest {
            combine(
                localState,
                latestArticles,
                articlesByKeyword
            ) { local, latestArticles, articlesByKeyword ->
                when {
                    latestArticles is Result.Success &&
                    articlesByKeyword is Result.Success -> {
                        HomeUiState.Success(
                            latestArticles = latestArticles.data,
                            articlesByKeyword = articlesByKeyword.data,
                            category = local.category,
                            keyword = local.keyword
                        )
                    }
                    else -> {
                        when {
                            latestArticles is Result.Error -> {
                                HomeUiState.Error(latestArticles.error.asUiText())
                            }
                            else -> {
                                val error = (articlesByKeyword as Result.Error).error.asUiText()
                                HomeUiState.Error(error)
                            }
                        }
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading
        )

    // Handled separately since works upon the user interaction with categories
    @OptIn(ExperimentalCoroutinesApi::class)
    val newsByCategory: StateFlow<PagingData<Article>> =
        localState.flatMapLatest { state ->
            getPagedNewsByCategoryUseCase(category = state.category)
                .cachedIn(viewModelScope)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PagingData.empty()
        )

    private fun retryFetchingArticles() {
        retryTrigger.update { it + 1 }
    }

    private fun onCategoryChange(category: String) {
        localState.update {
            it.copy(category = category)
        }
    }

    private fun onCategoryScrollPositionChange(scrollPosition: Int) {
        localState.update {
            it.copy(categoriesScrollPosition = scrollPosition)
        }
    }
}