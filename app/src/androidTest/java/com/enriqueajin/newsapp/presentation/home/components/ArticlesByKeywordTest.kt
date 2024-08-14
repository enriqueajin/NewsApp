package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ARTICLES_BY_KEYWORD_ITEM
import com.enriqueajin.newsapp.util.TestTags.ARTICLES_BY_KEYWORD_LAZY_ROW
import org.junit.Rule
import org.junit.Test

class ArticlesByKeywordTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun isArticlesByKeywordDisplayed() {
        val articles = DummyDataProvider.getAllNewsItems()

        composeTestRule.setContent {
            ArticlesByKeyword(news = articles) { }
        }

        composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_LAZY_ROW).assertIsDisplayed()
    }

    @Test
    fun isLazyRowScrollable() {
        val articles = DummyDataProvider.getAllNewsItems()

        composeTestRule.setContent {
            ArticlesByKeyword(news = articles) { }
        }

        composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_LAZY_ROW).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_LAZY_ROW).assert(hasScrollToNodeAction())
    }

    @Test
    fun areAllArticlesDisplayed() {
        val articles = DummyDataProvider.getAllNewsItems()

        composeTestRule.setContent {
            ArticlesByKeyword(news = articles) { }
        }

        articles.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_LAZY_ROW).performScrollToIndex(index)
            composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_ITEM + index).isDisplayed()
        }
    }

    @Test
    fun whenOnItemClicked_thenCallbackProvidesCorrectArticle() {
        val articles = DummyDataProvider.getAllNewsItems()
        val randomIndex = articles.indices.random()
        val expectedArticle = articles[randomIndex]
        var clickedArticle: Article? = null

        composeTestRule.setContent {
            ArticlesByKeyword(news = articles) { article -> clickedArticle = article }
        }

        composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_LAZY_ROW).performScrollToIndex(randomIndex)
        composeTestRule.onNodeWithTag(ARTICLES_BY_KEYWORD_ITEM + randomIndex).performClick()

        assert(clickedArticle == expectedArticle)
    }
}