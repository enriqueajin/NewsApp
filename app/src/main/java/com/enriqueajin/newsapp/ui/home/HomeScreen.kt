package com.enriqueajin.newsapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.home.components.TopTabRow

@Composable
fun Home() {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("News", "Events", "Weather")

    Box(modifier = Modifier.fillMaxSize()) {
        TopTabRow(tabIndex = tabIndex, tabs = tabs) { index ->
            tabIndex = index
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
    Home()
}