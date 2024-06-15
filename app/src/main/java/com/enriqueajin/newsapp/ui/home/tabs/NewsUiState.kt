package com.enriqueajin.newsapp.ui.home.tabs

import com.enriqueajin.newsapp.ui.model.NewsItem

data class NewsUiState(
    val isLoading: Boolean = false,
    val allTopNewsList: List<NewsItem> = emptyList(),
    val newsByKeywordList: List<NewsItem> = emptyList(),
    val error: String = ""
)
