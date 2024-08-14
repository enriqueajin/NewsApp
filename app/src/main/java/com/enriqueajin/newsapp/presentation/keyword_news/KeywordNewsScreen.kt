package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.PagingStateHandler
import com.enriqueajin.newsapp.presentation.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
internal fun KeywordScreenRoute(
    keywordNewsViewModel: KeywordNewsViewModel = hiltViewModel(),
    args: Route.KeywordNews,
    onItemClicked: (Article) -> Unit,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(Unit) {
        keywordNewsViewModel.setKeyword(args.keyword)
    }
    val articles = keywordNewsViewModel.articlesByKeyword.collectAsLazyPagingItems()

    KeywordNewsScreen(
        articles = articles,
        args = args,
        onItemClicked = onItemClicked,
        onBackPressed = onBackPressed
    )
}

@Composable
fun KeywordNewsScreen(
    articles: LazyPagingItems<Article>,
    args: Route.KeywordNews,
    onItemClicked: (Article) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(topBar = {
        KeywordNewsTopBarApp(
            title = args.keyword,
            onBackPressed = onBackPressed
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            PagingStateHandler(
                articles = articles,
                onItemClicked = onItemClicked
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
        args = Route.KeywordNews(keyword = "Recipes"),
        onItemClicked = {},
        onBackPressed = {}
    )
}