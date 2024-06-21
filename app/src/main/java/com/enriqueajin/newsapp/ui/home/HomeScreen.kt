package com.enriqueajin.newsapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.home.tabs.news.NewsViewModel
import com.enriqueajin.newsapp.ui.home.components.BottomNav
import com.enriqueajin.newsapp.ui.home.components.TopTabRow
import com.enriqueajin.newsapp.data.network.model.NewsItem

@Composable
fun Home(newsViewModel: NewsViewModel, onSeeAllClicked: (List<NewsItem>) -> Unit) {
    val tabs = listOf("News", "Events", "Weather")

    Scaffold(
        bottomBar = { BottomNav(newsViewModel) }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            TopTabRow(
                tabs = tabs,
                newsViewModel = newsViewModel,
                onSeeAllClicked = { onSeeAllClicked(it) }
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun HomePreview() {
//    Home(NewsViewModel())
}