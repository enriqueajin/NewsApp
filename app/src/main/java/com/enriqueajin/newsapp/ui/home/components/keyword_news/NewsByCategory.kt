package com.enriqueajin.newsapp.ui.home.components.keyword_news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import com.enriqueajin.newsapp.data.network.model.NewsItem

@RequiresApi(Build.VERSION_CODES.O)
fun LazyListScope.NewsByCategory(news: List<NewsItem>, onItemClicked: (NewsItem) -> Unit) {
    items(news) { item ->
        NewsListItem(item = item) { newsItem -> onItemClicked(newsItem) }
    }
}