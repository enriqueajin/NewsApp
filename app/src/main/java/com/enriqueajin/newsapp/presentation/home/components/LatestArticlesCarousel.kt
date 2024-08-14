package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.CAROUSEL_HORIZONTAL_PAGER
import com.enriqueajin.newsapp.util.TestTags.LATEST_ARTICLE_ITEM
import kotlin.math.absoluteValue

@Composable
fun LatestArticlesCarousel(news: List<Article>, onItemClicked: (Article) -> Unit) {
    val pagerState = rememberPagerState(pageCount = {
        news.size
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 30.dp),
        modifier = Modifier.testTag(CAROUSEL_HORIZONTAL_PAGER)
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
            LatestArticlesItem(
                modifier = Modifier.testTag(LATEST_ARTICLE_ITEM + index),
                article = item,
                onItemClicked = { article -> onItemClicked(article) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LatestArticlesCarouselPreview() {
    LatestArticlesCarousel(news = DummyDataProvider.getLatestNewsItems()) {}
}