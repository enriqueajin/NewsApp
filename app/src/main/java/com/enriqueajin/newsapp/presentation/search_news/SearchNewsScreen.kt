package com.enriqueajin.newsapp.presentation.search_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.presentation.home.components.keyword_news.NewsListItem
import com.enriqueajin.newsapp.util.Constants.HTTP_ERROR_UPGRADE_REQUIRED
import com.enriqueajin.newsapp.util.DummyDataProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsScreen(
    articles: LazyPagingItems<NewsItem>,
    event: (SearchNewsEvent) -> Unit,
    onItemClicked: (NewsItem) -> Unit,
) {

    var query by rememberSaveable { mutableStateOf("") }
    val focusRequest = FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    LaunchedEffect(query) {
        if (query.isNotBlank()) {
            event(SearchNewsEvent.OnQueryNews(query))
        }
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequest),
        query = query,
        onQueryChange = { text -> query = text },
        onSearch = { keyboardController?.hide() },
        active = true,
        onActiveChange = { },
        placeholder = { Text(text = "Search articles") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    query = ""
                },
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon"
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.White
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                // Initial load
                articles.loadState.refresh is LoadState.Loading && articles.itemCount == 0 -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                // No articles yet from Api
                articles.loadState.refresh is LoadState.NotLoading && articles.itemCount == 0 && query.isNotBlank() -> {
                    Text(
                        text = "There is not any articles.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Refresh button when failed
                articles.loadState.hasError && articles.itemCount == 0 -> {
                    Button(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = { articles.refresh() }) {
                        Text(text = "Retry")
                    }
                }

                else -> {
                    LazyColumn {
                        if (query.isNotBlank()) {
                            item { Spacer(modifier = Modifier.height(15.dp)) }

                            items(articles.itemCount) { index ->
                                articles[index]?.let { article ->
                                    NewsListItem(item = article) { newsItem -> onItemClicked(newsItem) }
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
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchNewsScreenPreview(modifier: Modifier = Modifier) {
    val items = DummyDataProvider.getAllNewsItems()
    SearchNewsScreen(
        articles = DummyDataProvider.getFakeLazyPagingItems(items),
        event = {},
        onItemClicked = {}
    )
}