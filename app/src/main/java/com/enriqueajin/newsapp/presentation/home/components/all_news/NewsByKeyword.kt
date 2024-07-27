package com.enriqueajin.newsapp.presentation.home.components.all_news

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.enriqueajin.newsapp.data.network.model.NewsItem

@Composable
fun NewsByKeyword(news: LazyPagingItems<NewsItem>, onItemClicked: (NewsItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        LazyRow {
            items(news.itemCount) {
                news[it]?.let { article ->
                    NewsByKeywordItem(article) { newsItem -> onItemClicked(newsItem) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsByKeywordPreview() {
//    NewsByKeyword(getAllNewsItems()) {}
}