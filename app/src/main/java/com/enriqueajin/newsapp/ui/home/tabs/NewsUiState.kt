package com.enriqueajin.newsapp.ui.home.tabs

import com.enriqueajin.newsapp.ui.model.NewsItem

sealed interface NewsUiState {
    object Loading: NewsUiState
    data class Success(
        val latestNews: List<NewsItem>? = null,
        val newsByKeyword: List<NewsItem>? = null,
        val newsByCategory: List<NewsItem>? = null
    ): NewsUiState
    data class Error(val throwable: Throwable): NewsUiState

}