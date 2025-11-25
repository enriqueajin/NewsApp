package com.enriqueajin.newsapp.presentation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailRoute
import com.enriqueajin.newsapp.presentation.bottom_bar.BottomBar
import com.enriqueajin.newsapp.presentation.bottom_bar.BottomBarItem
import com.enriqueajin.newsapp.presentation.favorites.FavoritesRoute
import com.enriqueajin.newsapp.presentation.home.HomeRoute
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordScreenRoute
import com.enriqueajin.newsapp.presentation.nav_graph.Route.Companion.getRoute
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsRoute
import kotlinx.serialization.json.Json

@Composable
fun NavGraph() {

    val items = remember {
        listOf(
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
    }

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val currentRoute = backStackEntry?.getRoute()

    selectedTab = updateSelectedNavItem(currentRoute)

    val isBottomBarVisible = remember(backStackEntry) { checkBottomBarVisible(currentRoute) }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(animationSpec = tween(600)) { it },
                exit = slideOutVertically(animationSpec = tween(600)) { it }
            ) {
                BottomBar(
                    items = items,
                    selectedItem = selectedTab,
                    onItemClick = { item ->
                        navController.navigateToTab( item.route)
                    }
                )
            }
        }
    ) {
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Route.Home
        ) {
            composable<Route.Home> {
                HomeRoute(navController = navController)
            }
            composable<Route.KeywordNews> { navBackStackEntry ->
                KeywordScreenRoute(navController = navController)
            }
            composable<Route.NewsDetail> { navBackStackEntry ->
                ArticleDetailRoute(navController = navController)
            }
            composable<Route.SearchNews> {
                SearchNewsRoute(navController = navController)
            }
            composable<Route.Favorites> {
                FavoritesRoute { item ->
                    val article = Json.encodeToString(Article.serializer(), item)
                    navController.navigateToDetail { Route.NewsDetail(article) }
                }
            }
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier,
    content:  NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        builder = content
    )
}

private fun updateSelectedNavItem(route: Route?) = when(route) {
    Route.Home -> 0
    Route.SearchNews -> 1
    Route.Favorites -> 2
    else -> 0
}

private fun checkBottomBarVisible(route: Route?) =
    route == Route.Home ||
    route == Route.SearchNews ||
    route == Route.Favorites
