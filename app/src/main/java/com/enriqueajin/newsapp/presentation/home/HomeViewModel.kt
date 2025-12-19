package com.enriqueajin.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.presentation.UiText
import com.enriqueajin.newsapp.presentation.asUiText
import com.enriqueajin.newsapp.presentation.home.HomeContract.Effect
import com.enriqueajin.newsapp.presentation.home.HomeContract.Event
import com.enriqueajin.newsapp.presentation.home.HomeContract.InternalEvent
import com.enriqueajin.newsapp.presentation.home.HomeContract.State
import com.enriqueajin.newsapp.util.KeywordProvider
import com.enriqueajin.newsapp.util.emitEffect
import com.enriqueajin.newsapp.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffects = _uiEffects.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val lazyArticlesByCategory = _uiState
        .map { it.category }
        .distinctUntilChanged()
        .flatMapLatest { category ->
            getNewsByCategoryUseCase(category)
        }
        .cachedIn(viewModelScope)

    init {
        pushInternal(InternalEvent.OnInit)
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCategoryChange -> _uiState.updateState { it.copy(category = event.category) }
            is Event.OnKeywordChange -> getArticlesByKeyword(keyword = event.keyword)
            is Event.OnItemClick -> _uiEffects.emitEffect(viewModelScope) { Effect.NavigateToArticleDetail(event.article) }
            is Event.OnSeeAllClick -> _uiEffects.emitEffect(viewModelScope) { Effect.NavigateToArticlesWithKeyword(event.keyword) }
            Event.OnRetry -> getInitialArticles()
        }
    }

    private fun pushInternal(internalEvent: InternalEvent) {
        when(internalEvent) {
            InternalEvent.OnInit -> getInitialArticles()
        }
    }

    private fun getInitialArticles() {
        getLatestArticles()
        getArticlesByKeyword(KeywordProvider.getRandomKeyword())
    }

    private fun getLatestArticles() {
        viewModelScope.launch {
            _uiState.updateState { it.copy(loading = true) }
            when(val result = getNewsByCategoryUseCase.getFixedSizeNewsByCategory()) {
                is Result.Error -> {
                    val errorMessage = result.error.asUiText()
                    _uiState.updateState { it.copy(error = errorMessage, loading = false) }
                }
                is Result.Success -> {
                    _uiState.updateState {
                        it.copy(
                            latestArticles = result.data,
                            loading = false,
                            error = UiText.empty()
                        )
                    }
                }
            }
        }
    }

    private fun getArticlesByKeyword(keyword: String) {
        viewModelScope.launch {
            when(val result = getNewsByKeywordUseCase.getFixedSizeNewsByKeyword(keyword)) {
                is Result.Error -> {
                    val errorMessage = result.error.asUiText()
                    _uiState.updateState { it.copy(error = errorMessage, loading = false) }
                }
                is Result.Success -> {
                    _uiState.updateState {
                        it.copy(
                            articlesByKeyword = result.data,
                            keyword = keyword,
                            loading = false,
                            error = UiText.empty(),
                        )
                    }
                }
            }
        }
    }
}
