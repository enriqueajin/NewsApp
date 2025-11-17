package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler

@Composable
fun ArticlesByCategory(
    modifier:  Modifier,
    articles: LazyPagingItems<Article>?,
    onItemClicked: (Article) -> Unit
) {
    articles?.let {
        PagingStateHandler(
            articles = it,
            onItemClicked = onItemClicked
        )
    }
}
