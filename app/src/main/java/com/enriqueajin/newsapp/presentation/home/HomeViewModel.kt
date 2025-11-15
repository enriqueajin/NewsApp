package com.enriqueajin.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.presentation.home.HomeContract.Effect
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
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

    init {
        onEvent(Event.OnInit)
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnInit -> getInitialArticles()
            is Event.OnCategoryChange -> getArticlesByCategory(category = event.category)
            is Event.OnKeywordChange -> getArticlesByKeyword(keyword = event.keyword)
            is Event.OnItemClick -> emitEffect(Effect.NavigateToArticleDetail(event.article))
            is Event.OnSeeAllClick -> emitEffect(Effect.NavigateToArticlesWithKeyword(event.keyword))
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }

    private fun getInitialArticles() {
        getLatestArticles()
        getArticlesByKeyword(KeywordProvider.getRandomKeyword())
    }

    private fun getLatestArticles() {
        _uiState.updateState { it.copy(loading = true) }
        viewModelScope.launch {
            getNewsByCategoryUseCase.getArticlesByCategory().distinctUntilChanged().collect { articles ->
                _uiState.value = _uiState.value.copy(
                    latestArticles = articles,
                    loading = false
                )
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
        _uiState.updateState { it.copy(loading = true) }
        viewModelScope.launch {
            getNewsByKeywordUseCase.getArticlesByKeyword(keyword).distinctUntilChanged().collect { articles ->
                _uiState.updateState {
                    it.copy(
                        articlesByKeyword = articles,
                        keyword = keyword,
                        loading = false
                    )
                }
            }
        }
    }
}
