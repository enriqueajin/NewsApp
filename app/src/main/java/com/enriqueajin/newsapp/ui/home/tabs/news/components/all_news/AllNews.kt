package com.enriqueajin.newsapp.ui.home.tabs.news.components.all_news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.DarkGray
import com.enriqueajin.newsapp.util.DummyDataProvider

fun LazyListScope.AllNews(
    latestNews: List<NewsItem>,
    previewKeywordNews: List<NewsItem>,
    onSeeAllClicked: (List<NewsItem>) -> Unit
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
                fontWeight = FontWeight.Bold,
                color = DarkGray,
                modifier = Modifier.padding(start = 30.dp).clickable { onSeeAllClicked(
                    DummyDataProvider.getAllNewsItems()) },
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        NewsByKeyword(previewKeywordNews)
    }
}