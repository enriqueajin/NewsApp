package com.enriqueajin.newsapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.home.components.BottomNav
import com.enriqueajin.newsapp.ui.home.components.all_news.AllNews
import com.enriqueajin.newsapp.ui.home.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.ui.home.components.keyword_news.NewsByCategory

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNav(homeViewModel) }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            val categories = listOf("All", "Science", "Technology", "Sports", "Health", "Business", "Entertainment")
            val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

            when(uiState) {
                is HomeUiState.Error -> Text(text = "${(uiState as HomeUiState.Error).throwable}")
                HomeUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is HomeUiState.Success -> {
                    val latestNews = (uiState as HomeUiState.Success).latestNews ?: emptyList()
                    val previewKeywordNews = (uiState as HomeUiState.Success).newsByKeyword ?: emptyList()
                    val newsByCategory = (uiState as HomeUiState.Success).newsByCategory ?: emptyList()

                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                    ) {
                        val selected = homeViewModel.localState.value.categorySelected

                        stickyHeader {
                            ChipGroup(
                                categories = categories,
                                selected = selected,
                                onChipSelected = { category ->
                                    homeViewModel.localState.value = homeViewModel.localState.value.copy(categorySelected = category)
                                    when (category) {
                                        "All" -> homeViewModel.getMainNews()
                                        else -> homeViewModel.getNewsByCategory(category, "50")
                                    }
                                }
                            )
                        }
                        when(selected) {
                            "All" -> {
                                AllNews(
                                    latestNews = latestNews,
                                    previewKeywordNews = previewKeywordNews,
                                    homeViewModel = homeViewModel,
                                    onSeeAllClicked = { news, keyword ->
                                        onSeeAllClicked(
                                            news,
                                            keyword
                                        )
                                    },
                                    onItemClicked = { newsItem -> onItemClicked(newsItem) }
                                )
                            }
                            else -> NewsByCategory(newsByCategory) { newsItem -> onItemClicked(newsItem) }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun HomePreview() {
//    Home(NewsViewModel())
}