package com.enriqueajin.newsapp.presentation.search_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.presentation.nav_graph.navigateToDetail
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsContract.State
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsContract.UiEvent
import com.enriqueajin.newsapp.util.collectAsEffect
import kotlinx.serialization.json.Json

@Composable
internal fun SearchNewsRoute(
    searchNewsViewModel: SearchNewsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by searchNewsViewModel.uiState.collectAsStateWithLifecycle()
    searchNewsViewModel.uiEffects.collectAsEffect { effect ->
        when(effect) {
            is SearchNewsContract.Effect.NavigateToArticleDetail -> {
                navController.navigateToDetail {
                    val article = Json.encodeToString(Article.serializer(), effect.article)
                    Route.NewsDetail(article)
                }
            }
        }
    }

    when {
        state.loading -> Loading()
        state.error.isNotBlank() -> Error()
        else -> {
            SearchNewsScreen(
                state = state,
                pushEvent = searchNewsViewModel::onPushEvent
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNewsScreen(
    state: State,
    pushEvent: (UiEvent) -> Unit,
) {
    val focusRequest = remember { FocusRequester() }
    var isActive by rememberSaveable { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val articles = state.searchedArticles.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequest),
        query = state.query,
        onQueryChange = { pushEvent(UiEvent.OnQueryChange(it)) },
        onSearch = { keyboardController?.hide() },
        active = isActive,
        onActiveChange = {
            isActive = it
            if (!isActive) pushEvent(UiEvent.OnQueryChange(""))
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
                    if (state.query == "") {
                        isActive = false
                    } else {
                        pushEvent(UiEvent.OnQueryChange(""))
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
                onItemClicked = { pushEvent(UiEvent.OnItemClick(it)) },
                query = state.query
            )
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
    }
}

@Composable
fun Error(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(text = "There was an error")
    }
}

@Preview(showBackground = true)
@Composable
fun SearchNewsScreenPreview(modifier: Modifier = Modifier) {
    SearchNewsScreen(
        state = State.EMPTY(),
        pushEvent = {},
    )
}