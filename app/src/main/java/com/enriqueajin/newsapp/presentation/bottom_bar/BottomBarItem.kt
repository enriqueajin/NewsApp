package com.enriqueajin.newsapp.presentation.bottom_bar

import androidx.compose.ui.graphics.vector.ImageVector
import com.enriqueajin.newsapp.presentation.nav_graph.Route

data class BottomBarItem(
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)