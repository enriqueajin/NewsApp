package com.enriqueajin.newsapp.presentation.favorites

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.UiText

sealed interface FavoritesUiState {

    data object Loading: FavoritesUiState

    data class Success(val favoriteArticles: List<Article>): FavoritesUiState

    data class Error(val error: UiText): FavoritesUiState
}