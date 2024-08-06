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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.keyword_news.NewsListItem
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun FavoritesScreen(
    event: (FavoritesEvent) -> Unit,
    searchText: String,
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
                event = event,
                articles = uiState.favoriteArticles,
                onItemClicked = { article -> onItemClicked(article) }
            )
        }
    }
}

@Composable
fun FavoriteList(
    searchText: String,
    event: (FavoritesEvent) -> Unit,
    articles: List<Article>,
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
            onValueChange = { text ->
                event(FavoritesEvent.OnSearchTextChanged(text))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        event(FavoritesEvent.OnSearchTextChanged(""))
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
                    NewsListItem(article) { onItemClicked(article) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        event = {},
        searchText = "",
        uiState = FavoritesUiState.Success(DummyDataProvider.getAllNewsItems()),
        onItemClicked = {}
    )
}