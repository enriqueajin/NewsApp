package com.enriqueajin.newsapp.ui.home.components.keyword_news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.home.HomeViewModel
import com.enriqueajin.newsapp.ui.home.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.Constants.HTTP_ERROR_UPGRADE_REQUIRED

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsByCategory(homeViewModel: HomeViewModel, onItemClicked: (NewsItem) -> Unit) {

    val category = homeViewModel.category.collectAsStateWithLifecycle().value
    val news = homeViewModel.newsByCategory.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // Initial load
            news.loadState.refresh is LoadState.Loading && news.itemCount == 0 -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            // No articles yet from Api
            news.loadState.refresh is LoadState.NotLoading && news.itemCount == 0 -> {
                Text(text = "There is not any articles.", modifier = Modifier.align(Alignment.Center))
            }

            // Refresh button when failed
            news.loadState.hasError && news.itemCount == 0 -> {
                Button(modifier = Modifier.align(Alignment.Center) , onClick = { news.refresh() }) {
                    Text(text = "Retry")
                }
            }

            else -> {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                ) {
                    stickyHeader {
                        ChipGroup(
                            categories = CATEGORIES,
                            selected = category,
                            onChipSelected = { category -> homeViewModel.category.value = category }
                        )
                    }
                    items(news.itemCount) {

                        news[it]?.let { article ->
                            NewsListItem(article) { newsItem -> onItemClicked(newsItem) }
                        }
                    }
                    item {
                        // Load indicator when scrolling
                        if (news.loadState.append is LoadState.Loading) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .align(Alignment.Center)
                                )
                            }
                        }
                        // Retry button when failed
                        if (news.loadState.append is LoadState.Error) {
                            val e = news.loadState.append as LoadState.Error
                            val errorMessage = e.error.localizedMessage?.trim() ?: ""

                            if (errorMessage != HTTP_ERROR_UPGRADE_REQUIRED) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Button(
                                        modifier = Modifier.align(Alignment.Center),
                                        onClick = { news.retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}