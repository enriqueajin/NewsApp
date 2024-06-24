package com.enriqueajin.newsapp.ui.home

import com.enriqueajin.newsapp.data.network.model.NewsItem

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Success(
        // External sources state
        val latestNews: List<NewsItem>? = null,
        val newsByKeyword: List<NewsItem>? = null,
        val newsByCategory: List<NewsItem>? = null,
        // Local state
        val categorySelected: String = "All",
        val keywords: String? = null
    ): HomeUiState
    data class Error(val throwable: Throwable): HomeUiState

}