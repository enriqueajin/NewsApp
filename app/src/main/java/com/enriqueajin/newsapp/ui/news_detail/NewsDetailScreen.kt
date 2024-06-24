package com.enriqueajin.newsapp.ui.news_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.enriqueajin.newsapp.ui.news_detail.components.NewsDetailsTopBar
import com.enriqueajin.newsapp.ui.theme.DarkGray
import com.enriqueajin.newsapp.ui.theme.Purple80
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_AUTHOR
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_CONTENT
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_PUBLISHED_AT
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_TITLE
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun NewsDetailScreen(newsItem: NewsItem, onBackPressed: () -> Unit) {
    Scaffold(topBar = { NewsDetailsTopBar { onBackPressed() }}) {
        Box(modifier = Modifier
            .padding(it)
            .padding(horizontal = 30.dp)
            .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(text = newsItem.author ?: NEWS_ITEM_AUTHOR) },
                        enabled = false,
                        colors = AssistChipDefaults.assistChipColors(
                            disabledContainerColor = Purple80,
                            disabledLabelColor = Color.White
                        )
                    )
                    Text(
                        text = newsItem.publishedAt ?: NEWS_ITEM_PUBLISHED_AT,
                        color = DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = newsItem.title ?: NEWS_ITEM_TITLE,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(25.dp))
                if (newsItem.urlToImage == null) {
                    Image(
                        painter = painterResource(id = R.drawable.no_image_available),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(230.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                } else {
                    AsyncImage(
                        model = newsItem.urlToImage,
                        error = painterResource(id = R.drawable.no_image_available),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = newsItem.content ?: NEWS_ITEM_CONTENT,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    NewsDetailScreen(DummyDataProvider.getAllNewsItems().first()) {

    }
}