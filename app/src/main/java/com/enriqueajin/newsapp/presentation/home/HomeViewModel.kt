package com.enriqueajin.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.presentation.UiText
import com.enriqueajin.newsapp.presentation.asUiText
import com.enriqueajin.newsapp.presentation.home.HomeContract.Effect
import com.enriqueajin.newsapp.presentation.home.HomeContract.Effect.*
import com.enriqueajin.newsapp.presentation.home.HomeContract.Event
import com.enriqueajin.newsapp.presentation.home.HomeContract.State
import com.enriqueajin.newsapp.util.KeywordProvider
import com.enriqueajin.newsapp.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.empty())
    val uiState = _uiState.asStateFlow()

    private val _uiEffects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffects = _uiEffects.asSharedFlow()

    private var lastEventAttempted: Event? = null

    init {
        onEvent(Event.OnInit)
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnInit -> getInitialArticles(event)
            is Event.OnCategoryChange -> getArticlesByCategory(category = event.category)
            is Event.OnKeywordChange -> getArticlesByKeyword(keyword = event.keyword)
            is Event.OnItemClick -> emitEffect(NavigateToArticleDetail(event.article))
            is Event.OnSeeAllClick -> emitEffect(NavigateToArticlesWithKeyword(event.keyword))
            Event.OnRetryClick -> lastEventAttempted?.let { onEvent(it) }
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }

    private fun getInitialArticles(event: Event) {
        lastEventAttempted = event
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

    private fun getArticlesByCategory(category: String) {
        _uiState.updateState { it.copy(loading = true) }
        viewModelScope.launch {
            val articles = getNewsByCategoryUseCase(category)
                .cachedIn(viewModelScope)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = PagingData.empty()
                )
            _uiState.updateState {
                it.copy(
                    newsByCategory = articles,
                    category = category,
                    loading = false
                )
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
