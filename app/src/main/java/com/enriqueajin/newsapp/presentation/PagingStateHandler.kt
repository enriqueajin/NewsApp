package com.enriqueajin.newsapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.ArticleItem
import com.enriqueajin.newsapp.util.Constants.HTTP_ERROR_UPGRADE_REQUIRED
import com.enriqueajin.newsapp.util.DummyDataProvider

/**
 * Custom composable to handle the states of the LazyPagingItems
 * @param articles list of articles of type LazyPagingItems
 * @param onItemClicked callback when an item is clicked
 * @param query optional parameter to filter the list of articles (SearchNewsScreen)
 */
@Composable
fun PagingStateHandler(
    articles: LazyPagingItems<Article>,
    onItemClicked: (Article) -> Unit,
    query: String? = null
) {
    when {
        // Initial load
        articles.loadState.refresh is LoadState.Loading && articles.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        // No articles yet from Api (when the screen isn't search tab)
        articles.loadState.refresh is LoadState.NotLoading && articles.itemCount == 0 && query == null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "There is not any articles.", modifier = Modifier.align(Alignment.Center))
            }
        }

        // No articles yet from Api (SearchNewsScreen)
        articles.loadState.refresh is LoadState.NotLoading && articles.itemCount == 0 && query?.isNotBlank() == true -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "There is not any articles.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Refresh button when failed
        articles.loadState.hasError && articles.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { articles.refresh() }) {
                    Text(text = "Retry")
                }
            }
        }

        else -> {
            if (query != null) {
                if (query.isNotBlank()) {
                    ArticleList(articles) { article ->
                        onItemClicked(article)
                    }
                }
            } else {
                ArticleList(articles) { article ->
                    onItemClicked(article)
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
    LazyColumn {
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

@Preview(showBackground = true)
@Composable
fun PagingStateHandlerPreview() {
    PagingStateHandler(
        articles = DummyDataProvider.getFakeLazyPagingItems(DummyDataProvider.getAllNewsItems()),
        onItemClicked = {},
    )
}