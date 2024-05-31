package com.enriqueajin.newsapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.home.components.bottom_navigation.BottomNav
import com.enriqueajin.newsapp.ui.home.components.tab_row.TopTabRow

@Composable
fun Home(newsViewModel: NewsViewModel) {
    val tabs = listOf("News", "Events", "Weather")

    Scaffold(
        bottomBar = { BottomNav(newsViewModel) }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            TopTabRow(
                tabs = tabs,
                newsViewModel = newsViewModel
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
    Home(NewsViewModel())
}