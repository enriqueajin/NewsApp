package com.enriqueajin.newsapp.ui.home.components.all_news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.util.DummyDataProvider.getLatestNewsItems
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatestNewsCarousel(news: List<NewsItem>) {
    val pagerState = rememberPagerState(pageCount = {
        news.size
    })

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 30.dp)
    ) { index ->
        Box(modifier = Modifier.graphicsLayer {
            val pageOffset by derivedStateOf {
                val offset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                offset.absoluteValue
            }

            // Carousel items size while flipping through
            lerp(
                start = 0.90f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            ).also { scale ->
                scaleX = scale
                scaleY = scale
            }

            // Carousel items opacity
            alpha = lerp(
                start = 0.5f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        }) {
            val item = news[index]
            LatestNewsItem(item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCarouselPreview() {
    LatestNewsCarousel(getLatestNewsItems())
}