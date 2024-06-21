package com.enriqueajin.newsapp.ui.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news.NewsListItem
import com.enriqueajin.newsapp.ui.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.data.network.model.NewsItem

@Composable
fun KeywordNewsScreen(
    news: List<NewsItem>,
    onItemClicked: (NewsItem) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(topBar = { KeywordNewsTopBarApp() { onBackPressed()} }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            LazyColumn {
                items(news) { item ->
                    NewsListItem(item)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KeywordNewsScreenPreview() {
//    KeywordNewsScreen()
}