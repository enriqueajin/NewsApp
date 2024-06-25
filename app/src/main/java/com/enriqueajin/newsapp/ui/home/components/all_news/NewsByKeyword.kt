package com.enriqueajin.newsapp.ui.home.components.all_news

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.util.DummyDataProvider.getAllNewsItems

@Composable
fun NewsByKeyword(news: List<NewsItem>, onItemClicked: (NewsItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        LazyRow {
            items(news) { item ->
                NewsByKeywordItem(item) { newsItem -> onItemClicked(newsItem) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsByKeywordPreview() {
    NewsByKeyword(getAllNewsItems()) {}
}