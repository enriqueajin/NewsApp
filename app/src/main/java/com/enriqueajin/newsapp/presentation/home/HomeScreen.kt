package com.enriqueajin.newsapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.HomeContract.State.Error
import com.enriqueajin.newsapp.presentation.home.HomeContract.State.Loading
import com.enriqueajin.newsapp.presentation.home.HomeContract.State.Success
import com.enriqueajin.newsapp.presentation.home.components.AllArticles
import com.enriqueajin.newsapp.presentation.home.components.ArticlesByCategory
import com.enriqueajin.newsapp.presentation.home.components.CategoryGroup
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_CIRCULAR_PROGRESS
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY

@Composable
internal fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (Article) -> Unit,
    onSeeAllClicked: (String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        Loading -> Loading()
        is Error -> Error()
        is Success -> {
            HomeScreen(
                uiState = (uiState as Success),
                onItemClicked = onItemClicked,
                onSeeAllClicked = onSeeAllClicked,
                event = homeViewModel::onEvent
            )
        }
    }
}

@Composable
fun HomeScreen(
    uiState: Success,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit,
    event: (HomeContract.Event) -> Unit,
) {
    val pagingItems = uiState.newsByCategory?.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            CategoryGroup(
                scrollPosition = 0,
                categories = CATEGORIES,
                selected = uiState.category,
                onChipSelected = {
                    event(HomeContract.Event.OnCategoryChange(it))
                },
            )
        }, modifier = Modifier.testTag(HOME)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if(uiState.category == CATEGORIES_INITIAL_VALUE) {
                AllArticles(
                    state = uiState,
                    onSeeAllClicked = onSeeAllClicked,
                    onItemClicked = onItemClicked
                )

            } else {
                ArticlesByCategory(
                    modifier = Modifier.testTag(HOME_ARTICLES_BY_CATEGORY),
                    articles = pagingItems,
                    onItemClicked = onItemClicked
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
    val state = Success(
        latestArticles = DummyDataProvider.getLatestNewsItems(),
        articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        keyword = "Recipes"
    )
    HomeScreen(
        uiState = state,
        onSeeAllClicked = {},
        onItemClicked = {},
        event = {}
    )
}