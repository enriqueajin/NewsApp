package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

@Composable
fun BottomNav(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(item) },
                icon = {
                    val icon = if (selectedItem == index) item.selectedIcon else item.unselectedIcon
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}