package com.enriqueajin.newsapp.ui.home.components.all_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.home.HomeViewModel
import com.enriqueajin.newsapp.ui.home.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.ui.theme.DarkGray
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun AllNews(
    homeViewModel: HomeViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit,
) {
    val latestNews = homeViewModel.latestNews.collectAsLazyPagingItems()
    val previewKeywordNews = homeViewModel.newsByKeyword.collectAsLazyPagingItems()

    val selected = homeViewModel.localState.collectAsStateWithLifecycle().value.categorySelected
    val category = homeViewModel.category.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            ChipGroup(
                categories = CATEGORIES,
                selected = category,
                onChipSelected = { category ->
                    homeViewModel.category.value = category
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when {
                // Initial load
                (latestNews.loadState.refresh is LoadState.Loading && latestNews.itemCount == 0) ||
                        (previewKeywordNews.loadState.refresh is LoadState.Loading && previewKeywordNews.itemCount == 0) -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                // No articles yet from Api
                (latestNews.loadState.refresh is LoadState.NotLoading && latestNews.itemCount == 0) ||
                        (previewKeywordNews.loadState.refresh is LoadState.NotLoading && previewKeywordNews.itemCount == 0) -> {
                    Text(text = "There is not any articles.", modifier = Modifier.align(Alignment.Center))
                }

                // Refresh button when failed
                (latestNews.loadState.hasError && latestNews.itemCount == 0) ||
                        (previewKeywordNews.loadState.hasError && previewKeywordNews.itemCount == 0) -> {
                    Button(modifier = Modifier.align(Alignment.Center) , onClick = {
                        latestNews.refresh()
                        previewKeywordNews.refresh()
                    }) {
                        Text(text = "Retry")
                    }
                }

                else -> {
                    ArticlesLists(
                        latestNews = latestNews,
                        previewKeywordNews = previewKeywordNews,
                        homeViewModel = homeViewModel,
                        onSeeAllClicked = { article, keyword -> onSeeAllClicked(article, keyword) },
                        onItemClicked = { article -> onItemClicked(article) }
                    )
                }
            }
        }
    }
}

@Composable
fun ArticlesLists(
    latestNews: LazyPagingItems<NewsItem>,
    previewKeywordNews: LazyPagingItems<NewsItem>,
    homeViewModel: HomeViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit,
) {
    val localState = homeViewModel.localState.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ) {
        val keyword = localState.value.keyword

        item {
            Text(
                text = "Latest news",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 30.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            LatestNewsCarousel(news = latestNews) { newsItem -> onItemClicked(newsItem) }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = keyword,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "See All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .clickable {
                            val news = DummyDataProvider.getAllNewsItems()
                            onSeeAllClicked(news, keyword)
                        },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            NewsByKeyword(previewKeywordNews) { newsItem -> onItemClicked(newsItem) }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}