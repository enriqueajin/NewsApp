package com.enriqueajin.newsapp.presentation.home

sealed interface HomeEvent {

    data class UpdateCategory(val category: String): HomeEvent

    data class UpdateCategoriesScrollPosition(val position: Int): HomeEvent
}