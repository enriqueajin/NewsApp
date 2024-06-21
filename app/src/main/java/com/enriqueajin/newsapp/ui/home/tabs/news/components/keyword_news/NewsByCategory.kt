package com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.enriqueajin.newsapp.data.network.model.NewsItem

fun LazyListScope.NewsByCategory(news: List<NewsItem>) {
    items(news) { item ->
        NewsListItem(item = item)
    }
}