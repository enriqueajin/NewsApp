package com.enriqueajin.newsapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.GetFavoriteArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase
): ViewModel() {

    private val favoriteArticles: Flow<List<Article>> = getFavoriteArticlesUseCase()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val uiState = searchText
        .combine(favoriteArticles) { text, favorites ->
            if (text.isBlank()) {
                FavoritesUiState.Success(favorites)
            } else {
                val filteredArticles = favorites.filter { it.title!!.contains(text, ignoreCase = true) }
                FavoritesUiState.Success(filteredArticles)
            }
        }.catch { error ->
            FavoritesUiState.Error(error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )

    fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnSearchTextChanged -> onSearchTextChange(event.text)
        }
    }

    private fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}