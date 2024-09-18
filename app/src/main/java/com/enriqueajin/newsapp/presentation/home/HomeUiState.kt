package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.UiText

sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Success(
        // External sources state
        val latestArticles: List<Article>? = null,
        val articlesByKeyword: List<Article>? = null,
        // Local state
        val category: String = "All",
        val keyword: String? = ""
    ): HomeUiState
//    data class Error(val throwable: Throwable): HomeUiState
    data class Error(val error: UiText): HomeUiState
}
