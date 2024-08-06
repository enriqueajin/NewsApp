package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.KeywordProvider

data class HomeLocalUiState (
    val latestArticles: List<Article> = emptyList(),
    val articlesByKeyword: List<Article> = emptyList(),
    val category: String = "All",
    val keyword: String = KeywordProvider.getRandomKeyword(),
    val categoriesScrollPosition: Int = 0
)