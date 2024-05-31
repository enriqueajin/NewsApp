package com.enriqueajin.newsapp.ui.home.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.home.components.ChipGroup
import com.enriqueajin.newsapp.ui.home.components.all_news_carousel.AllNewsCarousel
import com.enriqueajin.newsapp.ui.home.components.latest_news_carousel.LatestNewsCarousel
import com.enriqueajin.newsapp.ui.theme.DarkGray
import com.enriqueajin.newsapp.util.DummyDataProvider.getAllNewsItems
import com.enriqueajin.newsapp.util.DummyDataProvider.getLatestNewsItems

@Composable
fun News(newsViewModel: NewsViewModel) {
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
    val latestNews = getLatestNewsItems()
    val allNewsTop = getAllNewsItems()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
    ) {
        val selected = newsViewModel.chipSelected.value

        ChipGroup(
            categories = categories,
            selected = selected,
            onChipSelected = { category -> newsViewModel.setChipSelected(category) }
        )
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
                modifier = Modifier.padding(start = 30.dp),
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AllNewsCarousel(news = allNewsTop)
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun NewsPreview() {
    News(NewsViewModel())
}