package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler

@Composable
fun ArticlesByCategory(
    modifier:  Modifier,
    category: String,
    articles: LazyPagingItems<Article>?,
    onCollectArticlesByCategory: @Composable () -> Unit,
    onItemClicked: (Article) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Reset LazyPagingItems state when category changes
        key(category) {
            onCollectArticlesByCategory()

            if (articles != null) {
                PagingStateHandler(
                    articles = articles,
                    onItemClicked = onItemClicked
                )
            }
        }
    }
}