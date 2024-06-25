package com.enriqueajin.newsapp.ui.home.components.keyword_news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.DarkGray
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_AUTHOR
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_PUBLISHED_AT
import com.enriqueajin.newsapp.util.Constants.NEWS_ITEM_TITLE
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun NewsListItem(item: NewsItem, onItemClicked: (NewsItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .clickable { onItemClicked(item) }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (item.urlToImage == null) {
                Image(
                    painter = painterResource(id = R.drawable.no_image_available),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

            } else {
                AsyncImage(
                    model = item.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.no_image_available)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title ?: NEWS_ITEM_TITLE,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    maxLines = 3
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = item.author ?: NEWS_ITEM_AUTHOR,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = DarkGray
                    )
                    Text(
                        text = item.publishedAt ?: NEWS_ITEM_PUBLISHED_AT,
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
@Preview(showBackground = true)
@Composable
fun NewsListItemPreview() {
    NewsListItem(item = DummyDataProvider.getAllNewsItems().first(),) {}
}