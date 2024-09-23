package com.enriqueajin.newsapp.presentation.home

import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.UiText
import kotlinx.coroutines.flow.StateFlow

sealed interface HomeUiState {
    data object Loading: HomeUiState

    data class Success(
        // External sources state
        val latestArticles: List<Article>? = null,
        val articlesByKeyword: List<Article>? = null,
        val articlesByCategory: StateFlow<PagingData<Article>>? = null,
        // Local state
        val category: String = "All",
        val keyword: String? = ""
    ): HomeUiState

    data class Error(val error: UiText): HomeUiState
}
