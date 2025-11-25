package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.Effect
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.UiEvent
import com.enriqueajin.newsapp.presentation.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.presentation.nav_graph.navigateToDetail
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.collectAsEffect
import kotlinx.serialization.json.Json

@Composable
internal fun KeywordScreenRoute(
    keywordNewsViewModel: KeywordNewsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by keywordNewsViewModel.uiState.collectAsStateWithLifecycle()
    keywordNewsViewModel.uiEffect.collectAsEffect { effect ->
        when (effect) {
            Effect.NavigateBack -> navController.navigateUp()
            is Effect.NavigateToArticleDetail -> {
                navController.navigateToDetail {
                    val article = Json.encodeToString(Article.serializer(), effect.article)
                    Route.NewsDetail(article)
                }
            }
        }
    }

    when {
        state.loading -> CircularProgressIndicator()
        state.error.isNotBlank() -> Text("There was an error")
        else -> {
            val articles = state.articles.collectAsLazyPagingItems()
            KeywordNewsScreen(
                articles = articles,
                keyword = state.keyword,
                onPushEvent = keywordNewsViewModel::onPushEvent,
            )
        }
    }
}

@Composable
fun KeywordNewsScreen(
    articles: LazyPagingItems<Article>,
    keyword: String,
    onPushEvent: (UiEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            KeywordNewsTopBarApp(
                title = keyword,
                onBackPressed = { onPushEvent(UiEvent.OnBackPressed) }
            )
        }
    ) { innerPadding ->
        PagingStateHandler(
            modifier = Modifier.padding(innerPadding),
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
        keyword = "Recipes",
        onPushEvent = {}
    )
}
