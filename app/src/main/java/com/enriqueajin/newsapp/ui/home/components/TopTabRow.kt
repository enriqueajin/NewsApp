package com.enriqueajin.newsapp.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopTabRow(tabIndex: Int, tabs: List<String>, onTabIndexChanged: (Int) -> Unit) {
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        containerColor = Color.White,
        edgePadding = 0.dp,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.apply {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(4.dp)
                        .padding(horizontal = 28.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp))
                )
            }
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = tabIndex == index,
                onClick = { onTabIndexChanged(index) }
            )
        }
    }

    when (tabIndex) {
        0 -> News()
        1 -> Events()
        2 -> Weather()
    }
}