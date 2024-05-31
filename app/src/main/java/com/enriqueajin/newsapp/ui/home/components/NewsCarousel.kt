package com.enriqueajin.newsapp.ui.home.components

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
import com.enriqueajin.newsapp.ui.model.NewsItem
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsCarousel(news: List<NewsItem>) {
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
            val title = news[index].title ?: ""
            val datetime = news[index].publishedAt ?: ""
            CarouselNewsItem(title = title, datetime = datetime)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCarouselPreview() {
    val carouselItems = listOf(
        NewsItem(title = "Real Madrid champion of UEFA Champions League", publishedAt = "June 1st - 2024"),
        NewsItem(title = "Summer about to come in Poland", publishedAt = "May 31st - 2024"),
        NewsItem(title = "Chaos in traffic in Guatemala", publishedAt = "May 28th - 2024"),
        NewsItem(title = "Government offering loans to anyone", publishedAt = "June 3rd - 2024")
    )
    NewsCarousel(carouselItems)
}