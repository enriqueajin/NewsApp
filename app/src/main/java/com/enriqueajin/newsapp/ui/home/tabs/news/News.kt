package com.enriqueajin.newsapp.ui.home.tabs.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.enriqueajin.newsapp.ui.home.tabs.news.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.ui.home.tabs.news.components.all_news.AllNews
import com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news.NewsByCategory
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.update

@Composable
fun News(newsViewModel: NewsViewModel, onSeeAllClicked: (List<NewsItem>) -> Unit) {
    val categories = listOf("All", "Science", "Technology", "Sports", "Health", "Business", "Entertainment")

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<NewsUiState>(
        initialValue = NewsUiState.Loading,
        key1 = lifecycle,
        key2 = newsViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            newsViewModel.uiState.collect { value = it }
        }
    }

    when(uiState) {
        is NewsUiState.Error -> Text(text = "There is an error.")
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
                            newsViewModel.localState.update { currentState ->
                                currentState.copy(categorySelected = category)
                            }
                            when (category) {
                                "All" -> newsViewModel.getMainNews()
                                else -> newsViewModel.getNewsByCategory(category, "50")
                            }
                        }
                    )
                }
                when(selected) {
                    "All" -> AllNews(latestNews, previewKeywordNews) { onSeeAllClicked(it) }
                    else -> NewsByCategory(newsByCategory)
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