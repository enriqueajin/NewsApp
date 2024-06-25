package com.enriqueajin.newsapp.ui.home.components.all_news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.LightGray
import com.enriqueajin.newsapp.util.DummyDataProvider.getAllNewsItems

@Composable
fun NewsByKeywordItem(newsItem: NewsItem, onItemClicked: (NewsItem) -> Unit) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(200.dp)
            .padding(end = 15.dp)
            .clickable { onItemClicked(newsItem) },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            when {
                !newsItem.urlToImage.isNullOrEmpty() -> {
                    AsyncImage(
                        model = newsItem.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.TopCenter),
                        error = painterResource(id = R.drawable.no_image_available)
                    )
                } else -> {
                    Image(
                        painter = painterResource(id = R.drawable.no_image_available),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.TopCenter)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(90.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGray
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    val title = newsItem.title ?: "News without title"
                    Text(
                        text = title,
                        maxLines = 3,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
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
    NewsByKeywordItem(item) {}
}