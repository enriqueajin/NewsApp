package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.CAROUSEL_HORIZONTAL_PAGER
import com.enriqueajin.newsapp.util.TestTags.LATEST_ARTICLE_ITEM
import org.junit.Rule
import org.junit.Test

class LatestArticlesCarouselTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun isCarouselDisplayed() {
        val articles = DummyDataProvider.getLatestNewsItems()
        composeTestRule.setContent {
            LatestArticlesCarousel(news = articles, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).assertIsDisplayed()
    }

    @Test
    fun checkIsHorizontalPagerScrollable() {
        val articles = DummyDataProvider.getLatestNewsItems()
        composeTestRule.setContent {
            LatestArticlesCarousel(news = articles, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).assert(hasScrollToNodeAction())
    }

    @Test
    fun checkAreAllArticlesDisplayed() {
        val articles = DummyDataProvider.getLatestNewsItems()

        composeTestRule.setContent {
            LatestArticlesCarousel(news = articles, onItemClicked = {})
        }

        articles.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).performScrollTo()
            composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).assertIsDisplayed()
        }
    }

    @Test
    fun whenUserSwipeLeft_thenDisplayNextArticle() {
        val articles = DummyDataProvider.getLatestNewsItems()

        composeTestRule.setContent {
            LatestArticlesCarousel(news = articles, onItemClicked = {})
        }

        articles.forEachIndexed { index, _ ->
            if (index < articles.size) {
                composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).performScrollToIndex(index)
                composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).assertIsDisplayed()
                composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).performTouchInput {
                    swipeLeft()
                }

                // Assert that the next article is displayed. If it's the last article, assert that the current article is displayed
                if (index + 1 < articles.size) {
                    composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).performScrollToIndex(index + 1)
                    composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + (index + 1)).assertIsDisplayed()
                } else {
                    composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + (index)).assertIsDisplayed()
                }
            }
        }
    }

    @Test
    fun whenUserSwipeRight_thenDisplayPreviousArticle() {
        val articles = DummyDataProvider.getLatestNewsItems()

        composeTestRule.setContent {
            LatestArticlesCarousel(news = articles, onItemClicked = {})
        }

        for (index in articles.size - 1 downTo  0) {
            composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).performScrollToIndex(index)
            composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).assertIsDisplayed()
            composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + index).performTouchInput {
                swipeRight()
            }

            // Assert that the previous article is displayed. If it's the first article, assert that the current article is displayed
            if (index - 1 >= 0) {
                composeTestRule.onNodeWithTag(CAROUSEL_HORIZONTAL_PAGER).performScrollToIndex(index - 1)
                composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + (index - 1)).assertIsDisplayed()
            } else {
                composeTestRule.onNodeWithTag(LATEST_ARTICLE_ITEM + (index)).assertIsDisplayed()
            }
        }
    }
}