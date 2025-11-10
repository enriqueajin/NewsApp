package com.enriqueajin.newsapp.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.enriqueajin.newsapp.presentation.home.HomeContract.State.Success
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ARTICLES_LIST
import com.enriqueajin.newsapp.util.TestTags.HOME
import com.enriqueajin.newsapp.util.TestTags.HOME_ARTICLES_BY_CATEGORY
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule val composeTestRule = createComposeRule()

    private val state = Success(
        latestArticles = DummyDataProvider.getLatestNewsItems(),
        articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        keyword = "Recipes",
        category = CATEGORIES_INITIAL_VALUE,
    )

    @Test
    fun checkHomeScreenDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = state,
                onSeeAllClicked = {},
                onItemClicked = {},
                event = {}
            )
        }
        composeTestRule.onNodeWithTag(HOME).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsAll_thenAllArticlesIsDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = state,
                onSeeAllClicked = {},
                onItemClicked = {},
                event = {}
            )
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsAll_thenArticlesByCategoryIsNotDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = state,
                onSeeAllClicked = {},
                onItemClicked = {},
                event = {}
            )
        }
        composeTestRule.onNodeWithTag(HOME_ARTICLES_BY_CATEGORY).assertIsNotDisplayed()
    }

    @Test
    fun whenCategoryIsNotAll_thenArticlesByCategoryIsDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = state.copy(category = CATEGORIES.last()),
                onSeeAllClicked = {},
                onItemClicked = {},
                event = {}
            )
        }
        composeTestRule.onNodeWithTag(HOME_ARTICLES_BY_CATEGORY).assertIsDisplayed()
    }

    @Test
    fun whenCategoryIsNotAll_thenAllArticlesIsNotDisplayed() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = state.copy(category = CATEGORIES.last()),
                onSeeAllClicked = {},
                onItemClicked = {},
                event = {}
            )
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsNotDisplayed()
    }
}