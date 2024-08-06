package com.enriqueajin.newsapp.presentation.favorites

sealed interface FavoritesEvent {

    data class OnSearchTextChanged(val text: String): FavoritesEvent
}