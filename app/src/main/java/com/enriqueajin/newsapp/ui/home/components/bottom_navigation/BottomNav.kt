package com.enriqueajin.newsapp.ui.home.components.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.enriqueajin.newsapp.ui.NewsViewModel
import com.enriqueajin.newsapp.ui.model.BottomNavItem

@Composable
fun BottomNav(newsViewModel: NewsViewModel) {
    val items = getBottomNavItems()
    val selectedItemIndex = newsViewModel.selectedNavIndex.value

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { newsViewModel.setSelectedNavIndex(index) },
                icon = {
                    val icon = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon
                    Icon(
                        imageVector = icon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        BottomNavItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
    BottomNav(NewsViewModel())
}