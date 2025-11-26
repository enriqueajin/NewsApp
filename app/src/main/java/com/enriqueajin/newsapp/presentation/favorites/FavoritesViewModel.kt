package com.enriqueajin.newsapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.use_case.GetFavoriteArticlesUseCase
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.Effect
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.InternalEvent
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.State
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.UiEvent
import com.enriqueajin.newsapp.util.emitEffect
import com.enriqueajin.newsapp.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(State.EMPTY())
    val uiState = _uiState.asStateFlow()

    private val _uiEffects: MutableSharedFlow<Effect> = MutableSharedFlow()
    val uiEffects = _uiEffects.asSharedFlow()

    init {
        pushInternal(InternalEvent.OnInit)
    }

    fun pushEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnSearchTextChange -> getFavoriteArticles(event.text)
            UiEvent.OnBackPressed -> _uiEffects.emitEffect(viewModelScope) { Effect.NavigateBack }
            is UiEvent.OnItemClick -> _uiEffects.emitEffect(viewModelScope) { Effect.NavigateToArticleDetail(event.article) }
        }
    }

    private fun pushInternal(internalEvent: InternalEvent) {
        when (internalEvent) {
            InternalEvent.OnInit -> init()
        }
    }

    private fun init() {
        getFavoriteArticles(_uiState.value.searchText)
    }

    private fun getFavoriteArticles(query: String) {
        _uiState.updateState { it.copy(searchText = query) }
        viewModelScope.launch {
            getFavoriteArticlesUseCase()
                .map { list ->
                    if (query.isBlank()) {
                        list
                    } else {
                        list.filter {
                            it.title?.contains(query, ignoreCase = true) ?: false
                        }
                    }
                }
                .catch {
                    _uiState.updateState { it.copy(error = "There was an error", loading = false) }
                }
                .collect { articles ->
                    _uiState.updateState {
                        it.copy(
                            articles = articles,
                            loading = false
                        )
                    }
                }
        }
    }
}
