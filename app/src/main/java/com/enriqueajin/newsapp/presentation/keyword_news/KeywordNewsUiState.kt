package com.enriqueajin.newsapp.presentation.keyword_news

import com.enriqueajin.newsapp.data.network.model.NewsItem

sealed interface KeywordNewsUiState {
    object Loading: KeywordNewsUiState
    data class Success(val newsByKeyword: List<NewsItem>? = null): KeywordNewsUiState
    data class Error(val throwable: Throwable): KeywordNewsUiState
}