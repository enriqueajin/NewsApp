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
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.AllArticles
import com.enriqueajin.newsapp.presentation.home.components.ArticlesByCategory
import com.enriqueajin.newsapp.presentation.home.components.CategoryGroup
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY
import kotlinx.coroutines.flow.update

@Composable
internal fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onItemClicked: (Article) -> Unit,
    onSeeAllClicked: (String) -> Unit
) {
    val localState by homeViewModel.localState.collectAsStateWithLifecycle()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        localState = localState,
        uiState = uiState,
        onCategoryChange = { category -> homeViewModel.localState.update { it.copy(category = category) }},
        onCategoryScrollPositionChanged = { pos -> homeViewModel.localState.update { it.copy(categoriesScrollPosition = pos) } },
        onItemClicked = onItemClicked,
        onSeeAllClicked = onSeeAllClicked,
    )
}

@Composable
fun HomeScreen(
    localState: HomeLocalState,
    uiState: HomeUiState,
    onCategoryChange: (String) -> Unit,
    onCategoryScrollPositionChanged: (Int) -> Unit,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit
) {
    Scaffold(
        topBar = {
            CategoryGroup(
                scrollPosition = localState.categoriesScrollPosition,
                categories = CATEGORIES,
                selected = localState.category,
                onChipSelected = onCategoryChange,
                onCategoryScrollPositionChanged = onCategoryScrollPositionChanged,
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
                        onItemClicked = onItemClicked
                    )
                }
                else -> {
                    ArticlesByCategory(
                        modifier = Modifier.testTag(HOME_ARTICLES_BY_CATEGORY),
                        category = localState.category,
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
        onCategoryChange = {},
        onCategoryScrollPositionChanged = {},
        onSeeAllClicked = {},
        onItemClicked = {}
    )
}