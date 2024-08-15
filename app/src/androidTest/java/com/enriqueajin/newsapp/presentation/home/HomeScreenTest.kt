package com.enriqueajin.newsapp.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ARTICLES_LIST
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun checkHomeScreenDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                localState = HomeLocalState(),
                uiState = HomeUiState.Loading,
                articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
                onCollectArticlesByCategory = {},
                onCategoryChange = {},
                onCategoryScrollPositionChanged = {},
                onSeeAllClicked = {},
                onItemClicked = {}
            )
        }
        composeTestRule.onNodeWithTag(HOME).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsAll_thenAllArticlesIsDisplayed() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        )
        composeTestRule.setContent {
            HomeScreen(
                localState = HomeLocalState(category = "All"),
                uiState = state,
                articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
                onCollectArticlesByCategory = {},
                onCategoryChange = {},
                onCategoryScrollPositionChanged = {},
                onSeeAllClicked = {},
                onItemClicked = {}
            )
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsAll_thenArticlesByCategoryIsNotDisplayed() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        )
        composeTestRule.setContent {
            HomeScreen(
                localState = HomeLocalState(category = "All"),
                uiState = state,
                articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
                onCollectArticlesByCategory = {},
                onCategoryChange = {},
                onCategoryScrollPositionChanged = {},
                onSeeAllClicked = {},
                onItemClicked = {},
            )
        }
        composeTestRule.onNodeWithTag(HOME_ARTICLES_BY_CATEGORY).assertIsNotDisplayed()
    }

    @Test
    fun whenCategoryIsNotAll_thenArticlesByCategoryIsDisplayed() {
        // Get a random category different from 'All'
        val randomIndex = (1..<CATEGORIES.size).random()
        val category = CATEGORIES[randomIndex]

        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        )
        composeTestRule.setContent {
            HomeScreen(
                localState = HomeLocalState(category = category),
                uiState = state,
                articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
                onCollectArticlesByCategory = {},
                onCategoryChange = {},
                onCategoryScrollPositionChanged = {},
                onSeeAllClicked = {},
                onItemClicked = {},
            )
        }
        composeTestRule.onNodeWithTag(HOME_ARTICLES_BY_CATEGORY).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsNotAll_thenAllArticlesIsNotDisplayed() {
        // Get a random category different from 'All'
        val randomIndex = (1..<CATEGORIES.size).random()
        val category = CATEGORIES[randomIndex]

        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        )
        composeTestRule.setContent {
            HomeScreen(
                localState = HomeLocalState(category = category),
                uiState = state,
                articlesByCategory = DummyDataProvider.getFakeLazyPagingItems(data = DummyDataProvider.getAllNewsItems()),
                onCollectArticlesByCategory = {},
                onCategoryChange = {},
                onCategoryScrollPositionChanged = {},
                onSeeAllClicked = {},
                onItemClicked = {},
            )
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsNotDisplayed()
    }
}