package com.enriqueajin.newsapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.AllArticles
import com.enriqueajin.newsapp.presentation.home.components.CategoryGroup
import com.enriqueajin.newsapp.presentation.home.components.ArticlesByCategory
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    event: (HomeEvent) -> Unit,
    localState: HomeLocalUiState,
    uiState: HomeUiState,
    articlesStateFlow: StateFlow<PagingData<Article>>,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit
) {
    Scaffold(
        topBar = {
            CategoryGroup(
                event = event,
                scrollPosition = localState.categoriesScrollPosition,
                categories = CATEGORIES,
                selected = localState.category,
                onChipSelected = { category ->
                    event(HomeEvent.UpdateCategory(category))
                },
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
                        onSeeAllClicked = { keyword -> onSeeAllClicked(keyword) },
                        onItemClicked = { newsItem -> onItemClicked(newsItem) }
                    )
                }
                else -> ArticlesByCategory(
                    modifier = Modifier.testTag(HOME_ARTICLES_BY_CATEGORY),
                    articlesStateFlow = articlesStateFlow,
                    category = localState.category,
                    onItemClicked = { article -> onItemClicked(article) }
                )
            }
        }
    }
}

@Preview(showBackground = true,)
@Composable
fun HomePreview() {
    HomeScreen(
        event = {},
        localState = HomeLocalUiState(),
        uiState = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
            keyword = "Recipes"
        ),
        articlesStateFlow = MutableStateFlow(PagingData.empty()),
        onSeeAllClicked = {},
        onItemClicked = {}
    )
}