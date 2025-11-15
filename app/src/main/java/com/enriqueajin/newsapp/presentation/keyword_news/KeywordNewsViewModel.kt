package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.Effect
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.InternalEvent
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.State
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.UiEvent
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
class KeywordNewsViewModel @Inject constructor(
    private val savedSateHandle: SavedStateHandle,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
): ViewModel() {

    private val keyword: String? = savedSateHandle["keyword"]

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        keyword?.let {
            _uiState.updateState { state -> state.copy(keyword = it) }
        }
        onPushInternal(InternalEvent.OnInit)
    }

    fun onPushEvent(event: UiEvent) {
        when(event) {
            UiEvent.OnBackPressed -> emitEffect(Effect.NavigateBack)
            is UiEvent.OnItemClick -> emitEffect(Effect.NavigateToArticleDetail(event.article))
        }
    }

    private fun onPushInternal(internalEvent: InternalEvent) {
        when(internalEvent) {
            InternalEvent.OnInit -> getArticlesByKeyword(_uiState.value.keyword)
        }
    }

    private fun getArticlesByKeyword(keyword: String?) {
        _uiState.updateState { it.copy(loading = true) }
        keyword?.let {
            viewModelScope.launch {
                val articles = getNewsByKeywordUseCase(it)
                    .cachedIn(viewModelScope)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = PagingData.empty()
                    )
                _uiState.updateState { state -> state.copy(articles = articles, loading = false) }
            }
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
