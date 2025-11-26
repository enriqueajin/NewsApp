package com.enriqueajin.newsapp.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.ArticleItem
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.State
import com.enriqueajin.newsapp.presentation.favorites.FavoritesContract.UiEvent
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.presentation.nav_graph.navigateToDetail
import com.enriqueajin.newsapp.util.collectAsEffect
import kotlinx.serialization.json.Json

@Composable
internal fun FavoritesRoute(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by favoritesViewModel.uiState.collectAsStateWithLifecycle()
    favoritesViewModel.uiEffects.collectAsEffect { effect ->
        when(effect) {
            FavoritesContract.Effect.NavigateBack -> navController.navigateUp()
            is FavoritesContract.Effect.NavigateToArticleDetail -> {
                navController.navigateToDetail {
                    val article = Json.encodeToString(Article.serializer(), effect.article)
                    Route.NewsDetail(article)
                }
            }
        }
    }

    when {
        uiState.loading -> Loading()
        uiState.error.isNotBlank() -> Error()
        else -> {
            FavoritesScreen(
                state = uiState,
                onPushEvent = favoritesViewModel::pushEvent,
            )
        }
    }

}

@Composable
fun FavoritesScreen(
    state: State,
    onPushEvent: (UiEvent) -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                shape = RoundedCornerShape(10.dp),
                value = state.searchText,
                onValueChange = { onPushEvent(UiEvent.OnSearchTextChange(it)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { onPushEvent(UiEvent.OnSearchTextChange("")) },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                },
                placeholder = { Text(text = "Search favorites") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                singleLine = true
            )
            if (state.articles.isEmpty()) {
                Text(
                    text = "You don't have any favorite articles yet.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(state.articles) { article ->
                        ArticleItem(
                            article = article,
                            onItemClicked = { onPushEvent(UiEvent.OnItemClick(article)) }
                        )
                    }
                }
            }
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
fun FavoritesScreenPreview() {
    FavoritesScreen(
        state = State(
            loading = false,
            error = "",
            articles = DummyDataProvider.getAllNewsItems(),
            searchText = ""
        ),
        onPushEvent = {}
    )
}
