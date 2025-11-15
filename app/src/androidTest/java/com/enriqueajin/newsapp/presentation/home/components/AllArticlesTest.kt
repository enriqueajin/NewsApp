package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.enriqueajin.newsapp.presentation.home.HomeContract
import com.enriqueajin.newsapp.presentation.home.HomeContract.State
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

    private val state = State(
        latestArticles = DummyDataProvider.getLatestNewsItems(),
        articlesByKeyword = DummyDataProvider.getAllNewsItems(),
        keyword = "Recipes"
    )

    @Test
    fun whenStateIsSuccess_thenArticlesListsIsDisplayed() {
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}) {}
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ARTICLES_LIST).assertIsDisplayed()
    }

    @Test
    fun whenStateIsSuccess_thenLoadingAndErrorAreNotDisplayed() {
        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}) {}
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_CIRCULAR_PROGRESS).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(ALL_ARTICLES_ERROR).assertIsNotDisplayed()
    }

    @Test
    fun whenStateIsSuccess_thenDisplayExpectedKeyword() {
        val expectedKeyword = "Recipes"

        composeTestRule.setContent {
            AllArticles(state = state, onSeeAllClicked = {}) {}
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_KEYWORD)
            .assertIsDisplayed()
            .assert(hasText(expectedKeyword))
    }

    @Test
    fun whenUserClicksOnSeeAll_thenCallbackReturnsKeyword() {
        var keywordOnSeeAll: String? = null

        composeTestRule.setContent {
            AllArticles(
                state = state,
                onSeeAllClicked = { keyword -> keywordOnSeeAll = keyword }
            ) {}
        }
        composeTestRule.onNodeWithTag(ALL_ARTICLES_SEE_ALL)
            .assertIsDisplayed()
            .performClick()
        assert(state.keyword == keywordOnSeeAll)
    }
}