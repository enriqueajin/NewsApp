package com.enriqueajin.newsapp.presentation.home.components.all_news

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.presentation.home.HomeUiState
import com.enriqueajin.newsapp.presentation.ui.theme.DarkGray

@Composable
fun AllNews(
    state: HomeUiState,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (NewsItem) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is HomeUiState.Success -> {
                ArticlesLists(
                    latestArticles = (state as HomeUiState.Success).latestArticles ?: emptyList(),
                    articlesByKeyword = (state as HomeUiState.Success).articlesByKeyword ?: emptyList(),
                    keyword = (state as HomeUiState.Success).keyword ?: "",
                    onSeeAllClicked = { keyword -> onSeeAllClicked(keyword) },
                    onItemClicked = { article -> onItemClicked(article) }
                )
            }
            is HomeUiState.Error -> {
                val error = (state as HomeUiState.Error).throwable
                Text(text = "$error", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ArticlesLists(
    latestArticles: List<NewsItem>,
    articlesByKeyword: List<NewsItem>,
    keyword: String,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (NewsItem) -> Unit,
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ) {
        item {
            Text(
                text = "Latest news",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 30.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            LatestNewsCarousel(news = latestArticles) { article -> onItemClicked(article) }
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
                            onSeeAllClicked(keyword)
                        },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            NewsByKeyword(articlesByKeyword) { article -> onItemClicked(article) }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}