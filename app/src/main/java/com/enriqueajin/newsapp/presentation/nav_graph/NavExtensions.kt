package com.enriqueajin.newsapp.presentation.nav_graph

import androidx.navigation.NavController

fun NavController.navigateToDetail(routeBuilder: () -> Route) {
    navigate(routeBuilder()) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToTab(route: Route) {
    navigate(route) {
        graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}