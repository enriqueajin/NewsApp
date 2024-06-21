package com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.DarkGray

@Composable
fun NewsListItem(item: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = item.urlToImage,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title ?: "No title",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = item.author ?: "No author",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkGray
                    )
                    Text(
                        text = item.publishedAt ?: "No date",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = DarkGray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp))
        Spacer(modifier = Modifier.height(10.dp))

    }
}