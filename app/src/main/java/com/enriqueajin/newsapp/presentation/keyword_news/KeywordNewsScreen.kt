package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun KeywordNewsScreen(
    articles: LazyPagingItems<Article>,
    event: (KeywordNewsEvent) -> Unit,
    args: Route.KeywordNews,
    onItemClicked: (Article) -> Unit,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(args.keyword) {
        event(KeywordNewsEvent.GetArticlesByKeyword(args.keyword))
    }

    Scaffold(topBar = { KeywordNewsTopBarApp(args.keyword) { onBackPressed()} }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            PagingStateHandler(
                articles = articles,
                onItemClicked = { article -> onItemClicked(article)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeywordNewsScreenPreview() {
    val items = DummyDataProvider.getAllNewsItems()
    KeywordNewsScreen(
        articles = DummyDataProvider.getFakeLazyPagingItems(items),
        event = {},
        args = Route.KeywordNews(keyword = "Recipes"),
        onItemClicked = {},
        onBackPressed = {}
    )
}