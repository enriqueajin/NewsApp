package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.clickable
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
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.ui.theme.Purple80
import com.enriqueajin.newsapp.util.Constants.NO_DATE
import com.enriqueajin.newsapp.util.DateUtils
import com.enriqueajin.newsapp.util.DummyDataProvider.getLatestNewsItems

@Composable
fun LatestArticlesItem(article: Article, onItemClicked: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onItemClicked(article) },
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
                val title = article.title ?: "Item without title"
                val publishedAt = article.publishedAt ?: "May, 31st - 2024"
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                maxLines = 4
            )
            Text(
                text = DateUtils.formatDate(article.publishedAt ?: NO_DATE),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LatestArticlesItemPreview() {
    val item = getLatestNewsItems().first()
    LatestArticlesItem(item) {}
}