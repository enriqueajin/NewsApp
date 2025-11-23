package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.Effect
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.UiEvent
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.collectAsEffect

@Composable
internal fun KeywordScreenRoute(
    keywordNewsViewModel: KeywordNewsViewModel = hiltViewModel(),
    onNavigationEffect: (Effect) -> Unit,
) {
    val state by keywordNewsViewModel.uiState.collectAsStateWithLifecycle()
    keywordNewsViewModel.uiEffect.collectAsEffect { onNavigationEffect(it) }

    when {
        state.loading -> CircularProgressIndicator()
        state.error.isNotBlank() -> Text("There was an error")
        else -> {
            val articles = state.articles.collectAsLazyPagingItems()
            KeywordNewsScreen(
                articles = articles,
                onPushEvent = keywordNewsViewModel::onPushEvent,
            )
        }
    }
}

@Composable
fun KeywordNewsScreen(
    articles: LazyPagingItems<Article>,
    onPushEvent: (UiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PagingStateHandler(
            articles = articles,
            onItemClicked = { article ->
                onPushEvent(UiEvent.OnItemClick(article))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeywordNewsScreenPreview() {
    val items = DummyDataProvider.getAllNewsItems()
    KeywordNewsScreen(
        articles = DummyDataProvider.getFakeLazyPagingItems(items),
        onPushEvent = {}
    )
}
