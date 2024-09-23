package com.enriqueajin.newsapp.presentation.home

sealed interface HomeEvent {

    data class OnCategoryChange(val category: String): HomeEvent

    data class OnCategoryScrollPositionChange(val scrollPosition: Int): HomeEvent

    data object OnRetry: HomeEvent
}