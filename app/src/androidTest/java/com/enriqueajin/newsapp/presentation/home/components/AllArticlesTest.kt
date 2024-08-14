package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.enriqueajin.newsapp.presentation.home.HomeUiState
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ARTICLES_LIST
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_CIRCULAR_PROGRESS
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ERROR
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_KEYWORD
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_SEE_ALL
import org.junit.Rule
import org.junit.Test

class AllArticlesTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun whenStateIsLoading_thenCircularProgressIndicatorIsDisplayed() {
        composeTestRule.setContent {
            AllArticles(state = HomeUiState.Loading, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_CIRCULAR_PROGRESS).assertIsDisplayed()
    }

    @Test
    fun whenStateIsLoading_thenSuccessAndErrorAreNotDisplayed() {
        composeTestRule.setContent {
            AllArticles(state = HomeUiState.Loading, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ERROR).assertIsNotDisplayed()
    }

    @Test
    fun whenStateIsSuccess_thenArticlesListsIsDisplayed() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
            keyword = "Recipes"
        )
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsDisplayed()
    }

    @Test
    fun whenStateIsSuccess_thenLoadingAndErrorAreNotDisplayed() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
            keyword = "Recipes"
        )
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_CIRCULAR_PROGRESS).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ERROR).assertIsNotDisplayed()
    }

    @Test
    fun whenStateIsSuccess_thenDisplayExpectedKeyword() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
            keyword = "Recipes"
        )
        val expectedKeyword = "Recipes"

        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_KEYWORD)
            .assertIsDisplayed()
            .assert(hasText(expectedKeyword))
    }

    @Test
    fun whenUserClicksOnSeeAll_thenCallbackReturnsKeyword() {
        val state = HomeUiState.Success(
            latestArticles = DummyDataProvider.getLatestNewsItems(),
            articlesByKeyword = DummyDataProvider.getAllNewsItems(),
            keyword = "Smartphone"
        )
        var keywordOnSeeAll: String? = null

        composeTestRule.setContent {
            AllArticles(
                state = state,
                onSeeAllClicked = { keyword -> keywordOnSeeAll = keyword },
                onItemClicked = {}
            )
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_SEE_ALL)
            .assertIsDisplayed()
            .performClick()
        assert(state.keyword == keywordOnSeeAll)
    }

    @Test
    fun whenStateIsError_thenErrorIsDisplayed() {
        val state = HomeUiState.Error(Throwable("Error"))
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ERROR).assertIsDisplayed()
    }

    @Test
    fun whenStateIsError_thenLoadingAndSuccessAreNotDisplayed() {
        val state = HomeUiState.Error(Throwable("Error"))
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}, onItemClicked = {})
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_CIRCULAR_PROGRESS).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsNotDisplayed()
    }
}