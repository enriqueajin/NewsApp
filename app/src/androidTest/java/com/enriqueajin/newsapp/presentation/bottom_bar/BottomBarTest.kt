package com.enriqueajin.newsapp.presentation.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.enriqueajin.newsapp.presentation.nav_graph.Route
import com.enriqueajin.newsapp.presentation.ui.theme.NewsAppTheme
import com.enriqueajin.newsapp.util.TestTags.BOTTOM_BAR_ITEM_FAVORITES
import com.enriqueajin.newsapp.util.TestTags.BOTTOM_BAR_ITEM_HOME
import com.enriqueajin.newsapp.util.TestTags.BOTTOM_BAR_ITEM_SEARCH
import org.junit.Rule
import org.junit.Test

class BottomBarTest {

    @get:Rule val composeTestRule = createComposeRule()

    private var selectedItem by mutableIntStateOf(0)
    private val items = listOf(
        BottomBarItem(
            route = Route.Home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomBarItem(
            route = Route.SearchNews,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        BottomBarItem(
            route = Route.Favorites,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Default.FavoriteBorder
        )
    )


    @Test
    fun whenAppLaunched_thenHomeIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        // Assertions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_HOME).assertIsSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).assertIsNotSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).assertIsNotSelected()
    }

    @Test
    fun whenHomeTabIsClicked_thenHomeIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            // Simulate that Home isn't already selected
            selectedItem = 1

            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        // Actions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_HOME).performClick()

        // Assertions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_HOME).assertIsSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).assertIsNotSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).assertIsNotSelected()
    }

    @Test
    fun whenSearchTabIsClicked_thenSearchIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        // Actions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).performClick()

        // Assertions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_HOME).assertIsNotSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).assertIsSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).assertIsNotSelected()
    }

    @Test
    fun whenFavoritesTabIsClicked_thenFavoritesIsSelectedAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        // Actions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).performClick()

        // Assertions
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_HOME).assertIsNotSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).assertIsNotSelected()
        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).assertIsSelected()
    }

    @Test
    fun whenHomeIsSelected_thenHomeIconIsActiveAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        // Assertions
        val homeItem = items[selectedItem]
        assert(homeItem.selectedIcon == Icons.Filled.Home)
        assert(homeItem.selectedIcon != Icons.Filled.Search)
        assert(homeItem.selectedIcon != Icons.Filled.Favorite)
    }

    @Test
    fun whenSearchIsSelected_thenSearchIconIsActiveAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_SEARCH).performClick()

        // Assertions
        val searchItem = items[selectedItem]
        assert(searchItem.selectedIcon != Icons.Filled.Home)
        assert(searchItem.selectedIcon == Icons.Filled.Search)
        assert(searchItem.selectedIcon != Icons.Filled.Favorite)
    }

    @Test
    fun whenFavoritesIsSelected_thenFavoritesIconIsActiveAndOthersArent() {
        composeTestRule.setContent {
            NewsAppTheme {
                BottomBar(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = { item -> selectedItem = items.indexOf(item)}
                )
            }
        }

        composeTestRule.onNodeWithTag(BOTTOM_BAR_ITEM_FAVORITES).performClick()

        // Assertions
        val favoritesItem = items[selectedItem]
        assert(favoritesItem.selectedIcon != Icons.Filled.Home)
        assert(favoritesItem.selectedIcon != Icons.Filled.Search)
        assert(favoritesItem.selectedIcon == Icons.Filled.Favorite)
    }
}