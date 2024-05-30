package com.enriqueajin.newsapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.utils.CategoryProvider.getCategories

@Composable
fun News() {
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
    val options = getCategories(categories)

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
    ) {
        ChipGroup(categories = options)
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
fun NewsPreview() {
    News()
}