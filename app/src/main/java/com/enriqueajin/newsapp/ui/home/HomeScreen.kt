package com.enriqueajin.newsapp.ui.home

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.home.components.BottomNav
import com.enriqueajin.newsapp.ui.home.components.all_news.AllNews
import com.enriqueajin.newsapp.ui.home.components.chip_group.ChipGroup
import com.enriqueajin.newsapp.ui.home.components.keyword_news.NewsByCategory
import com.enriqueajin.newsapp.util.Constants.CATEGORIES

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit
) {
    val category = homeViewModel.category.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = { BottomNav(homeViewModel) },
        topBar = {
            ChipGroup(
                homeViewModel = homeViewModel,
                categories = CATEGORIES,
                selected = category.value,
                onChipSelected = { category -> homeViewModel.setCategory(category) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (category.value) {
                "All" -> {
                    AllNews(
                        homeViewModel = homeViewModel,
                        onSeeAllClicked = { news, keyword -> onSeeAllClicked(news, keyword) },
                        onItemClicked = { newsItem -> onItemClicked(newsItem) }
                    )
                }
                else -> NewsByCategory(homeViewModel) { newsItem -> onItemClicked(newsItem) }

            }
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