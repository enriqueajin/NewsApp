package com.enriqueajin.newsapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.home.components.tab_row.TopTabRow

@Composable
fun Home(newsViewModel: NewsViewModel) {
    val tabIndex = newsViewModel.tabRowIndex.value
    val tabs = listOf("News", "Events", "Weather")

    Box(modifier = Modifier.fillMaxSize()) {
        TopTabRow(
            tabIndex = tabIndex,
            tabs = tabs,
            newsViewModel = newsViewModel,
            onTabIndexChanged = { index -> newsViewModel.setTabRowIndex(index) }
        )
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