package com.enriqueajin.newsapp.ui.home.components.all_news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.Purple80
import com.enriqueajin.newsapp.util.DummyDataProvider.getLatestNewsItems

@Composable
fun LatestNewsItem(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Purple80,
            contentColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 40.dp,
                    top = 20.dp,
                    bottom = 20.dp
                ), verticalArrangement = Arrangement.SpaceBetween) {
                val title = newsItem.title ?: "Item without title"
                val publishedAt = newsItem.publishedAt ?: "May, 31st - 2024"
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )
            Text(
                text = publishedAt,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarouselNewsItemPreview() {
    val item = getLatestNewsItems().first()
    LatestNewsItem(item)
}