package com.enriqueajin.newsapp.ui.home.components.tab_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.home.tabs.Events
import com.enriqueajin.newsapp.ui.home.tabs.News
import com.enriqueajin.newsapp.ui.home.tabs.Weather
import com.enriqueajin.newsapp.ui.model.NewsItem
import kotlinx.coroutines.launch

@Composable
fun TopTabRow(
    tabs: List<String>,
    newsViewModel: NewsViewModel,
    onSeeAllClicked: (List<NewsItem>) -> Unit
) {
    val tabRowPagerState = rememberPagerState(pageCount = {
        tabs.size
    })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = tabRowPagerState.currentPage,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.apply {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabRowPagerState.currentPage])
                            .height(4.dp)
                            .padding(horizontal = 28.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                    )
                }
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = true,
                    onClick = {
                        coroutineScope.launch {
                            tabRowPagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = tabRowPagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {index ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (index) {
                    0 -> News(newsViewModel) { onSeeAllClicked(it) }
                    1 -> Events()
                    2 -> Weather()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopTabRowPreview() {
//    val tabs = listOf("News", "Events", "Weather")
//    TopTabRow(
//        tabs = tabs,
//        newsViewModel = NewsViewModel()
//    )
}