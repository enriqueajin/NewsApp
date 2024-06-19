package com.enriqueajin.newsapp.ui.home.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.home.components.ChipGroup
import com.enriqueajin.newsapp.ui.home.components.all_news_carousel.AllNewsCarousel
import com.enriqueajin.newsapp.ui.home.components.latest_news_carousel.LatestNewsCarousel
import com.enriqueajin.newsapp.ui.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.DarkGray

@Composable
fun News(newsViewModel: NewsViewModel) {
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")

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
            val newsByKeyword = (uiState as NewsUiState.Success).newsByKeyword ?: emptyList()

            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ) {
                val selected = newsViewModel.chipSelected.value

                item {
                    ChipGroup(
                        categories = categories,
                        selected = selected,
                        onChipSelected = { category -> newsViewModel.setChipSelected(category) }
                    )
                }
                when(selected) {
                    "All" -> allNews(latestNews = latestNews, allTopNews = newsByKeyword)
                    else -> newsByCategory(news = newsByKeyword)
                }
            }
        }
    }
}

fun LazyListScope.allNews(
    latestNews: List<NewsItem>,
    allTopNews: List<NewsItem>,
) {
    item {
        Text(
            text = "Latest news",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 30.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LatestNewsCarousel(news = latestNews)
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "All news",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See All",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 30.dp),
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AllNewsCarousel(news = allTopNews)
    }
}

fun LazyListScope.newsByCategory(news: List<NewsItem>) {
    items(news) { item ->
        NewsListItem(item = item)
    }
}

@Composable
fun NewsListItem(item: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = item.urlToImage,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title ?: "No title",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = item.author ?: "No author",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkGray
                    )
                    Text(
                        text = item.publishedAt ?: "No date",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = DarkGray
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Spacer(modifier = Modifier.height(10.dp))

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