package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.util.KeywordProvider

data class HomeLocalUiState (
    val latestArticles: List<NewsItem> = emptyList(),
    val articlesByKeyword: List<NewsItem> = emptyList(),
    val category: String = "All",
    val keyword: String = KeywordProvider.getRandomKeyword(),
    val categoriesScrollPosition: Int = 0
)