package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ArticlesByCategory(
    articlesStateFlow: StateFlow<PagingData<Article>>,
    category: String,
    onItemClicked: (Article) -> Unit
) {
    // Reset LazyPagingItems state when category changes
    key(category) {
        val articles = articlesStateFlow.collectAsLazyPagingItems()
        PagingStateHandler(
            articles = articles,
            onItemClicked = { article -> onItemClicked(article) }
        )
    }
}