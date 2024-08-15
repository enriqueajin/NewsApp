package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.enriqueajin.newsapp.presentation.ui.theme.NewsAppTheme
import com.enriqueajin.newsapp.util.TestTags.CATEGORY
import com.enriqueajin.newsapp.util.TestTags.CATEGORY_GROUP_LAZY_ROW
import org.junit.Rule
import org.junit.Test

class CategoryGroupTest {

    @get:Rule val composeTestRule = createComposeRule()

    private val categories = listOf("All", "Science", "Technology", "Sports", "Health", "Business", "Entertainment")
    private var selected by mutableStateOf("All")

    @Test
    fun checkCategoryGroupIsScrollable() {
        composeTestRule.setContent {
            CategoryGroup(
                scrollPosition = 0,
                categories = categories,
                selected = selected,
                onChipSelected = { category -> selected = category },
                onCategoryScrollPositionChanged = {}
            )
        }
        composeTestRule.onNodeWithTag(CATEGORY_GROUP_LAZY_ROW).assert(hasScrollAction())
    }

    @Test
    fun whenAppLaunched_thenAllIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                CategoryGroup(
                    scrollPosition = 0,
                    categories = categories,
                    selected = selected,
                    onChipSelected = { category -> selected = category },
                    onCategoryScrollPositionChanged = {}
                )
            }
        }

        // Get nodes through categories list since 'onAllNodes' doesn't retrieve nodes that aren't visible on the screen
        categories.forEachIndexed { index, _ ->
            // Scroll to current node within LazyRow
            composeTestRule.onNodeWithTag(CATEGORY_GROUP_LAZY_ROW).performScrollToIndex(index)

            // Get current node
            val item = composeTestRule.onNodeWithTag(CATEGORY + index)

            // Check that the current node is selected and others aren't
            if (categories[index] == "All" && selected == "All") {
                item.assertIsSelected()
            } else {
                item.assertIsNotSelected()
            }
        }
    }

    /**
     * Get a random category, click on it and check that it's selected and others aren't
     */
    @Test
    fun whenCategoryIsClicked_thenAllIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                CategoryGroup(
                    scrollPosition = 0,
                    categories = categories,
                    selected = selected,
                    onChipSelected = { category -> selected = category },
                    onCategoryScrollPositionChanged = {}
                )
            }
        }

        // Get a random node and click on it
        val randomIndex = categories.indices.random()
        val category = categories[randomIndex]
        composeTestRule.onNodeWithTag(CATEGORY_GROUP_LAZY_ROW).performScrollToIndex(randomIndex)
        composeTestRule.onNodeWithTag(CATEGORY + randomIndex).performClick()

        // Get nodes through categories list since 'onAllNodes' doesn't retrieve nodes that aren't visible on the screen
        categories.forEachIndexed { index, _ ->
            // Scroll to current node within LazyRow
            composeTestRule.onNodeWithTag(CATEGORY_GROUP_LAZY_ROW).performScrollToIndex(index)

            // Get current node
            val item = composeTestRule.onNodeWithTag(CATEGORY + index)

            // Check that the current node is selected and others aren't
            if (categories[index] == category && selected == category) {
                item.assertIsSelected()
            } else {
                item.assertIsNotSelected()
            }
        }
    }
}