package com.enriqueajin.newsapp.presentation.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

@Composable
fun BottomBar(
    items: List<BottomBarItem>,
    selectedItem: Int,
    onItemClick: (BottomBarItem) -> Unit
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