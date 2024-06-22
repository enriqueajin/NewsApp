package com.enriqueajin.newsapp.ui.home.tabs.news

import com.enriqueajin.newsapp.data.network.model.NewsItem

sealed interface NewsUiState {
    object Loading: NewsUiState
    data class Success(
        // External sources state
        val latestNews: List<NewsItem>? = null,
        val newsByKeyword: List<NewsItem>? = null,
        val newsByCategory: List<NewsItem>? = null,
        // Local state
        val categorySelected: String = "All",
        val keywords: String? = null
    ): NewsUiState
    data class Error(val throwable: Throwable): NewsUiState

}