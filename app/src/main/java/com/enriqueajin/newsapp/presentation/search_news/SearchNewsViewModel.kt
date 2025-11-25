package com.enriqueajin.newsapp.presentation.search_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsContract.Effect
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsContract.State
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsContract.UiEvent
import com.enriqueajin.newsapp.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(State.EMPTY())
    val uiState = _uiState.asStateFlow()

    private val _uiEffects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffects = _uiEffects.asSharedFlow()

    fun onPushEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnQueryChange -> getNewsByKeyword(event.query)
            is UiEvent.OnItemClick -> emitEffect(Effect.NavigateToArticleDetail(event.article))
        }
    }

    @OptIn(FlowPreview::class)
    private fun getNewsByKeyword(query: String) {
        _uiState.updateState { it.copy(query = query) }
        viewModelScope.launch {
            if(query.isNotBlank()) {
                val articles = getNewsByKeywordUseCase(query)
                    .debounce(500L)
                    .cachedIn(viewModelScope)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000),
                        initialValue = PagingData.empty()
                    )
                _uiState.updateState { it.copy(searchedArticles = articles) }
            }
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffects.emit(effect)
        }
    }
}