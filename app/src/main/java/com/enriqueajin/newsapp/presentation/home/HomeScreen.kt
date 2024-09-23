package com.enriqueajin.newsapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.AllArticles
import com.enriqueajin.newsapp.presentation.home.components.ArticlesByCategory
import com.enriqueajin.newsapp.presentation.home.components.CategoryGroup
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY

@Composable
internal fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (Article) -> Unit,
    onSeeAllClicked: (String) -> Unit,
) {
    val localState by homeViewModel.localState.collectAsStateWithLifecycle()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val articlesByCategory = homeViewModel.newsByCategory.collectAsLazyPagingItems()

    HomeScreen(
        localState = localState,
        uiState = uiState,
        event = homeViewModel::onEvent,
        articlesByCategory = articlesByCategory,
        onItemClicked = onItemClicked,
        onSeeAllClicked = onSeeAllClicked,
    )
}

@Composable
fun HomeScreen(
    localState: HomeLocalState,
    uiState: HomeUiState,
    event: (HomeEvent) -> Unit,
    articlesByCategory: LazyPagingItems<Article>,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit,
) {
    Scaffold(
        topBar = {
            CategoryGroup(
                scrollPosition = localState.categoriesScrollPosition,
                categories = CATEGORIES,
                selected = localState.category,
                onChipSelected = { category ->
                    event(HomeEvent.OnCategoryChange(category))
                },
                onCategoryScrollPositionChanged = { scrollPosition ->
                    event(HomeEvent.OnCategoryScrollPositionChange(scrollPosition))
                }
            )
        }, modifier = Modifier.testTag(HOME)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (localState.category) {
                "All" -> {
                    AllArticles(
                        state = uiState,
                        onSeeAllClicked = onSeeAllClicked,
                        onItemClicked = onItemClicked,
                        onRetry = {
                            event(HomeEvent.OnRetry)
                        },
                    )
                }
                else -> {
                    ArticlesByCategory(
                        modifier = Modifier.testTag(HOME_ARTICLES_BY_CATEGORY),
                        articlesByCategory = articlesByCategory,
                        onItemClicked = onItemClicked
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val state = HomeUiState.Success(
        latestArticles = DummyDataProvider.getLatestNewsItems(),
        articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        keyword = "Recipes"
    )
    HomeScreen(
        localState = HomeLocalState(),
        uiState = state,
        event = {},
        articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
        onSeeAllClicked = {},
        onItemClicked = {},
    )
}