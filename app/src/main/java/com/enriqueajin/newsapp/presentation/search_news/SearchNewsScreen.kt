package com.enriqueajin.newsapp.presentation.search_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.presentation.home.components.keyword_news.NewsListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsScreen(
    searchViewModel: SearchNewsViewModel,
    onItemClicked: (NewsItem) -> Unit
) {
    var isActive by rememberSaveable { mutableStateOf(true) }
    val focusRequest = FocusRequester()

    LaunchedEffect(isActive) {
        focusRequest.requestFocus()
    }

    val searchText = searchViewModel.searchText.collectAsStateWithLifecycle()

    key(isActive) {
        val articles = searchViewModel.articlesSearched.collectAsLazyPagingItems()

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequest),
            query = searchText.value,
            onQueryChange = { searchViewModel.onSearchTextChange(it) },
            onSearch = { isActive = false },
            active = isActive,
            onActiveChange = { isActive = it },
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
                        if (searchText.value.isNotEmpty()) {
                            searchViewModel.onSearchTextChange("")
                        }
                        isActive = false
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            },
        ) {
            LazyColumn {
                items(articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        NewsListItem(item = article) { newsItem -> onItemClicked(newsItem) }
                    }
                }
            }
        }
    }
}