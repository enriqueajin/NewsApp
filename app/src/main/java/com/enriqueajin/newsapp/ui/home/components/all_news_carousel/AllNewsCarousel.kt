package com.enriqueajin.newsapp.ui.home.components.all_news_carousel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.ui.model.NewsItem
import com.enriqueajin.newsapp.util.DummyDataProvider.getAllNewsItems

@Composable
fun AllNewsCarousel(news: List<NewsItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        LazyRow {
            items(news) { item ->
                AllNewsItem(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllNewsCarouselPreview() {
    AllNewsCarousel(getAllNewsItems())
}