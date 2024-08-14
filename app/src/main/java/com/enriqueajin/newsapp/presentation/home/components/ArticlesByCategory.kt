package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.home.HomeViewModel

@Composable
fun ArticlesByCategory(
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier:  Modifier,
    category: String,
    onItemClicked: (Article) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Reset LazyPagingItems state when category changes
        key(category) {
            val articles = homeViewModel.newsByCategory.collectAsLazyPagingItems()
            PagingStateHandler(
                articles = articles,
                onItemClicked = { article -> onItemClicked(article) }
            )
        }
    }
}