package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.presentation.Route
import com.enriqueajin.newsapp.presentation.home.components.keyword_news.NewsListItem
import com.enriqueajin.newsapp.presentation.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.util.Constants.HTTP_ERROR_UPGRADE_REQUIRED

@Composable
fun KeywordNewsScreen(
    keywordNewsViewModel: KeywordNewsViewModel,
    args: Route.KeywordNews,
    onItemClicked: (NewsItem) -> Unit,
    onBackPressed: () -> Unit
) {
    val keyword = args.keyword
    LaunchedEffect(key1 = keyword) {
        keywordNewsViewModel.getNewsByKeyword(keyword)
    }

    val news = keywordNewsViewModel.newsByKeyword.collectAsLazyPagingItems()

    Scaffold(topBar = { KeywordNewsTopBarApp(keyword) { onBackPressed()} }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            when {
                // Initial load
                news.loadState.refresh is LoadState.Loading && news.itemCount == 0 -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                // No articles yet from Api
                news.loadState.refresh is LoadState.NotLoading && news.itemCount == 0 -> {
                    Text(text = "There is not any articles.", modifier = Modifier.align(Alignment.Center))
                }

                // Refresh button when failed
                news.loadState.hasError && news.itemCount == 0 -> {
                    Button(modifier = Modifier.align(Alignment.Center) , onClick = { news.refresh() }) {
                        Text(text = "Retry")
                    }
                }

                else -> {
                    LazyColumn {
                        items(news.itemCount) {
                            news[it]?.let { article ->
                                NewsListItem(article) { newsItem -> onItemClicked(newsItem) }
                            }
                        }
                        item {
                            // Load indicator when scrolling
                            if (news.loadState.append is LoadState.Loading) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(Alignment.Center)
                                    )
                                }
                            }
                            // Retry button when failed
                            if (news.loadState.append is LoadState.Error) {
                                val e = news.loadState.append as LoadState.Error
                                val errorMessage = e.error.localizedMessage?.trim() ?: ""

                                if (errorMessage != HTTP_ERROR_UPGRADE_REQUIRED) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Button(
                                            modifier = Modifier.align(Alignment.Center),
                                            onClick = { news.retry() }) {
                                            Text(text = "Retry")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KeywordNewsScreenPreview() {
//    KeywordNewsScreen()
}