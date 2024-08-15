package com.enriqueajin.newsapp.presentation.favorites

import com.enriqueajin.newsapp.domain.model.Article

sealed interface FavoritesUiState {

    data object Loading: FavoritesUiState

    data class Success(val favoriteArticles: List<Article>): FavoritesUiState

    data class Error(val throwable: Throwable): FavoritesUiState
}