package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.enriqueajin.newsapp.presentation.Route
import com.enriqueajin.newsapp.presentation.home.HomeViewModel

@Composable
fun BottomNav(navController: NavController, homeViewModel: HomeViewModel) {

    val items = getBottomNavItems()
    val selectedTabIndex = homeViewModel.selectedTabIndex.collectAsStateWithLifecycle()

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTabIndex.value == index,
                onClick = {
                    homeViewModel.setSelectedTabIndex(index)
                    navController.navigate(item.route)
                },
                icon = {
                    val icon = if (selectedTabIndex.value == index) item.selectedIcon else item.unselectedIcon
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            route = Route.Home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            route = Route.SearchNews,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),
        BottomNavItem(
            route = Route.Favorites,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Default.FavoriteBorder
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview() {
//    BottomNav(NewsViewModel())
}