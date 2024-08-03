package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.data.network.model.NewsItem

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Success(
        // External sources state
        val latestArticles: List<NewsItem>? = null,
        val articlesByKeyword: List<NewsItem>? = null,
        // Local state
        val category: String = "All",
        val keyword: String? = ""
    ): HomeUiState
    data class Error(val throwable: Throwable): HomeUiState
}