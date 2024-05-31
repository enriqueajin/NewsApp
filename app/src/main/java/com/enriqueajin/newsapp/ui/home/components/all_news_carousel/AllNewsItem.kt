package com.enriqueajin.newsapp.ui.home.components.all_news_carousel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.ui.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.LightGray
import com.enriqueajin.newsapp.util.DummyDataProvider.getAllNewsItems

@Composable
fun AllNewsItem(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(200.dp)
            .padding(end = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = newsItem.urlToImage,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGray
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val title = newsItem.title ?: "News without title"
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal = 15.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllNewsItemPreview() {
    val item = getAllNewsItems().first()
    AllNewsItem(item)
}