package com.enriqueajin.newsapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.model.NewsItem

@Composable
fun News(newsViewModel: NewsViewModel) {
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
    val carouselItems = listOf(
        NewsItem(title = "Real Madrid champion of UEFA Champions League", publishedAt = "June 1st - 2024"),
        NewsItem(title = "Summer about to come in Poland", publishedAt = "May 31st - 2024"),
        NewsItem(title = "Chaos in traffic in Guatemala", publishedAt = "May 28th - 2024"),
        NewsItem(title = "Government offering loans to anyone", publishedAt = "June 3rd - 2024")
    )

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
        NewsCarousel(news = carouselItems)
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