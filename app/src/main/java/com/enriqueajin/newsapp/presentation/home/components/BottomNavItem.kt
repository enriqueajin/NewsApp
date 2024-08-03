package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.enriqueajin.newsapp.presentation.Route

data class BottomNavItem(
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)