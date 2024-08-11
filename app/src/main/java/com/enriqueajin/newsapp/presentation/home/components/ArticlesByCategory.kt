package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.Constants.HTTP_ERROR_UPGRADE_REQUIRED
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

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                // Initial load
                articles.loadState.refresh is LoadState.Loading && articles.itemCount == 0 -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                // No articles yet from Api
                articles.loadState.refresh is LoadState.NotLoading && articles.itemCount == 0 -> {
                    Text(text = "There is not any articles.", modifier = Modifier.align(Alignment.Center))
                }

                // Refresh button when failed
                articles.loadState.hasError && articles.itemCount == 0 -> {
                    Button(modifier = Modifier.align(Alignment.Center) , onClick = { articles.refresh() }) {
                        Text(text = "Retry")
                    }
                }

                else -> {
                    ArticleList(articles) { article ->
                        onItemClicked(article)
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleList(
    articles: LazyPagingItems<Article>,
    onItemClicked: (Article) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(articles.itemCount) {
            articles[it]?.let { article ->
                ArticleItem(article) { newsItem -> onItemClicked(newsItem) }
            }
        }

        item {
            // Load indicator when scrolling
            if (articles.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Center)
                    )
                }
            }

            // Retry button when failed
            if (articles.loadState.append is LoadState.Error) {
                val e = articles.loadState.append as LoadState.Error
                val errorMessage = e.error.localizedMessage?.trim() ?: ""

                if (errorMessage != HTTP_ERROR_UPGRADE_REQUIRED) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = { articles.retry() }) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        }
    }
}