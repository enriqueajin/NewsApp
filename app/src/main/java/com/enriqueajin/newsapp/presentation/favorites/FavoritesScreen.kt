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
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.ArticleItem
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
internal fun FavoritesRoute(
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onItemClicked: (Article) -> Unit,
) {
    val uiState by favoritesViewModel.uiState.collectAsStateWithLifecycle()
    val searchText by favoritesViewModel.searchText.collectAsStateWithLifecycle()

    FavoritesScreen(
        searchText = searchText,
        onSearchTextChanged = favoritesViewModel::onSearchTextChange,
        uiState = uiState,
        onItemClicked = onItemClicked
    )
}

@Composable
fun FavoritesScreen(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    uiState: FavoritesUiState,
    onItemClicked: (Article) -> Unit
) {
    when (uiState) {
        is FavoritesUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "${uiState.throwable}", modifier = Modifier.align(Alignment.Center))
            }
        }
        FavoritesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is FavoritesUiState.Success -> {
            FavoriteList(
                searchText = searchText,
                articles = uiState.favoriteArticles,
                onSearchTextChanged = onSearchTextChanged,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun FavoriteList(
    searchText: String,
    articles: List<Article>,
    onSearchTextChanged: (String) -> Unit,
    onItemClicked: (Article) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            shape = RoundedCornerShape(10.dp),
            value = searchText,
            onValueChange = onSearchTextChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onSearchTextChanged("")
                    },
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
        if (articles.isEmpty()) {
            Text(
                text = "You don't have any favorite articles yet.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(articles) { article ->
                    ArticleItem(
                        article = article,
                        onItemClicked = onItemClicked
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        searchText = "",
        onSearchTextChanged = {},
        uiState = FavoritesUiState.Success(DummyDataProvider.getAllNewsItems()),
        onItemClicked = {}
    )
}