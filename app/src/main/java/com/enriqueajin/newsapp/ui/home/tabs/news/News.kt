package com.enriqueajin.newsapp.ui.home.tabs.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.home.tabs.news.components.all_news.AllNews
import com.enriqueajin.newsapp.ui.home.tabs.news.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news.NewsByCategory

@Composable
fun News(
    newsViewModel: NewsViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit
) {
    val categories = listOf("All", "Science", "Technology", "Sports", "Health", "Business", "Entertainment")
    val uiState by newsViewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is NewsUiState.Error -> Text(text = "${(uiState as NewsUiState.Error).throwable}")
        NewsUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is NewsUiState.Success -> {
            val latestNews = (uiState as NewsUiState.Success).latestNews ?: emptyList()
            val previewKeywordNews = (uiState as NewsUiState.Success).newsByKeyword ?: emptyList()
            val newsByCategory = (uiState as NewsUiState.Success).newsByCategory ?: emptyList()

            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ) {
                val selected = newsViewModel.localState.value.categorySelected

                item {
                    ChipGroup(
                        categories = categories,
                        selected = selected,
                        onChipSelected = { category ->
                            newsViewModel.localState.value = newsViewModel.localState.value.copy(categorySelected = category)
                            when (category) {
                                "All" -> newsViewModel.getMainNews()
                                else -> newsViewModel.getNewsByCategory(category, "50")
                            }
                        }
                    )
                }
                when(selected) {
                    "All" -> AllNews(latestNews, previewKeywordNews, newsViewModel) { news, keyword -> onSeeAllClicked(news, keyword) }
                    else -> NewsByCategory(newsByCategory) { newsItem -> onItemClicked(newsItem) }
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
fun NewsPreview() {
//    News(NewsViewModel())
}