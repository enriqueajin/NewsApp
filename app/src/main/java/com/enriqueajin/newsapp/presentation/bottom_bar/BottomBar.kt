package com.enriqueajin.newsapp.presentation.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.enriqueajin.newsapp.util.TestTags.BOTTOM_BAR_ITEM

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
                },
                modifier = Modifier.testTag(BOTTOM_BAR_ITEM + index)
            )
        }
    }
}