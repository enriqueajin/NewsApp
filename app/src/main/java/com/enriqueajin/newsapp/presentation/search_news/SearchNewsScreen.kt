package com.enriqueajin.newsapp.presentation.search_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.util.DummyDataProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsScreen(
    articles: LazyPagingItems<Article>,
    event: (SearchNewsEvent) -> Unit,
    onItemClicked: (Article) -> Unit,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val focusRequest = remember { FocusRequester() }
    var isActive by rememberSaveable { mutableStateOf(true) }
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
        active = isActive,
        onActiveChange = {
            isActive = it
            if (!isActive) query = ""
        },
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
                    if (query == "") {
                        isActive = false
                    } else {
                        query = ""
                    }
                },
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon"
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PagingStateHandler(
                articles = articles,
                onItemClicked = { article -> onItemClicked(article) },
                query = query
            )
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