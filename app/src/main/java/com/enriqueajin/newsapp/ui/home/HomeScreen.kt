package com.enriqueajin.newsapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.enriqueajin.newsapp.ui.home.components.keyword_news.NewsByCategory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onSeeAllClicked: (List<NewsItem>, String) -> Unit,
    onItemClicked: (NewsItem) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNav(homeViewModel) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

//            val selected = homeViewModel.localState.collectAsStateWithLifecycle().value.categorySelected
            val category = homeViewModel.category.collectAsStateWithLifecycle().value

            when (category) {
                "All" -> {
                    AllNews(
                        homeViewModel = homeViewModel,
                        onSeeAllClicked = { news, keyword ->
                            onSeeAllClicked(
                                news,
                                keyword
                            )
                        },
                        onItemClicked = { newsItem -> onItemClicked(newsItem) }
                    )
                }
                else -> {
                    NewsByCategory(homeViewModel) { newsItem -> onItemClicked(newsItem) }
                }
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