package com.enriqueajin.newsapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.HomeContract.Event
import com.enriqueajin.newsapp.presentation.home.HomeContract.State
import com.enriqueajin.newsapp.presentation.home.HomeContract.Effect
import com.enriqueajin.newsapp.presentation.home.components.AllArticles
import com.enriqueajin.newsapp.presentation.home.components.ArticlesByCategory
import com.enriqueajin.newsapp.presentation.home.components.CategoryGroup
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.presentation.nav_graph.navigateToDetail
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_CIRCULAR_PROGRESS
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY
import com.enriqueajin.newsapp.util.collectAsEffect
import kotlinx.serialization.json.Json

@Composable
internal fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    homeViewModel.uiEffects.collectAsEffect { effect ->
        when (effect) {
            is Effect.NavigateToArticleDetail -> {
                navController.navigateToDetail {
                    val article = Json.encodeToString(Article.serializer(), effect.article)
                    Route.NewsDetail(article)
                }
            }
            is Effect.NavigateToArticlesWithKeyword -> navController.navigateToDetail { Route.KeywordNews(effect.keyword) }
        }
    }

    when {
        uiState.loading -> Loading()
        uiState.error.isNotBlank() -> Error()
        else -> {
            HomeScreen(
                uiState = uiState,
                event = homeViewModel::onEvent
            )
        }
    }
}

@Composable
fun HomeScreen(
    uiState: State,
    event: (Event) -> Unit,
) {
    val pagingItems = uiState.newsByCategory?.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.testTag(HOME)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CategoryGroup(
                scrollPosition = 0,
                categories = CATEGORIES,
                selected = uiState.category,
                onChipSelected = {
                    event(Event.OnCategoryChange(it))
                },
            )
            Spacer(modifier = Modifier.height(5.dp))
            if(uiState.category == CATEGORIES_INITIAL_VALUE) {
                AllArticles(
                    state = uiState,
                    onSeeAllClicked = { keyword ->
                        event(Event.OnSeeAllClick(keyword))
                    },
                ) { article ->
                    event(Event.OnItemClick(article))
                }

            } else {
                ArticlesByCategory(
                    modifier = Modifier.testTag(HOME_ARTICLES_BY_CATEGORY),
                    articles = pagingItems,
                    onItemClicked = { article ->
                        event(Event.OnItemClick(article))
                    },
                )
            }
        }

    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier
            .align(Alignment.Center)
            .testTag(ALL_ARTICLES_CIRCULAR_PROGRESS)
        )
    }
}

@Composable
fun Error(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        CircularProgressIndicator(modifier = Modifier
            .align(Alignment.Center)
            .testTag(ALL_ARTICLES_CIRCULAR_PROGRESS)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val state = State(
        latestArticles = DummyDataProvider.getLatestNewsItems(),
        articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        keyword = "Recipes"
    )
    HomeScreen(
        uiState = state,
        event = {},
    )
}