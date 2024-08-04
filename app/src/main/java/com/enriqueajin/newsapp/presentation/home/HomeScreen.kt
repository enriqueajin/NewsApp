package com.enriqueajin.newsapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.components.all_news.AllNews
import com.enriqueajin.newsapp.presentation.home.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.presentation.home.components.keyword_news.NewsByCategory
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    event: (HomeEvent) -> Unit,
    localState: HomeLocalUiState,
    uiState: HomeUiState,
    articlesStateFlow: StateFlow<PagingData<Article>>,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ChipGroup(
                event = event,
                scrollPosition = localState.categoriesScrollPosition,
                categories = CATEGORIES,
                selected = localState.category,
                onChipSelected = { category ->
                    scope.launch {
                        event(HomeEvent.UpdateCategory(category))
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (localState.category) {
                "All" -> {
                    AllNews(
                        state = uiState,
                        onSeeAllClicked = { keyword -> onSeeAllClicked(keyword) },
                        onItemClicked = { newsItem -> onItemClicked(newsItem) }
                    )
                }
                else -> NewsByCategory(
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