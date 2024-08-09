package com.enriqueajin.newsapp.presentation.nav_graph

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.favorites.FavoritesScreen
import com.enriqueajin.newsapp.presentation.favorites.FavoritesViewModel
import com.enriqueajin.newsapp.presentation.home.HomeScreen
import com.enriqueajin.newsapp.presentation.home.HomeViewModel
import com.enriqueajin.newsapp.presentation.home.components.BottomNav
import com.enriqueajin.newsapp.presentation.home.components.BottomNavItem
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsScreen
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsViewModel
import com.enriqueajin.newsapp.presentation.news_detail.NewsDetailScreen
import com.enriqueajin.newsapp.presentation.news_detail.NewsDetailViewModel
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsScreen
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsViewModel
import kotlinx.serialization.json.Json

@Composable
fun NavGraph() {

    val items = remember {
        listOf(
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

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = when (backStackEntry?.destination?.route) {
        Route.Home::class.qualifiedName -> 0
        Route.SearchNews::class.qualifiedName -> 1
        Route.Favorites::class.qualifiedName -> 2
        else -> 0
    }

    // Hide bottom bar when the user is in KeywordScreen or NewsDetailScreen
    val isBottomBarVisible = remember(key1 = backStackEntry) {
        backStackEntry?.destination?.route == Route.Home::class.qualifiedName ||
        backStackEntry?.destination?.route == Route.SearchNews::class.qualifiedName ||
        backStackEntry?.destination?.route == Route.Favorites::class.qualifiedName
    }

    Scaffold(bottomBar = {
        if (isBottomBarVisible) {
            BottomNav(
                items = items,
                selectedItem = selectedItem,
                onItemClick = { item ->
                    navigateToTab(navController, item.route)
                }
            )
        }
    }) {
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            Modifier.padding(it)
        ) {
            composable<Route.Home> {
                val viewModel: HomeViewModel = hiltViewModel()
                val localState = viewModel.localState.value
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val articlesStateFlow = viewModel.newsByCategory
                HomeScreen(
                    event = viewModel::onEvent,
                    localState = localState,
                    uiState = uiState,
                    articlesStateFlow = articlesStateFlow,
                    onSeeAllClicked = { keyword ->
                        navigateToDetail(navController) {
                            Route.KeywordNews(keyword)
                        }
                    },
                    onItemClicked = { newsItem ->
                        val newsArg = Json.encodeToString(Article.serializer(), newsItem)
                        navigateToDetail(navController) {
                            Route.NewsDetail(newsArg)
                        }
                    }
                )
            }
            composable<Route.KeywordNews> {
                val args = it.toRoute<Route.KeywordNews>()
                val viewModel: KeywordNewsViewModel = hiltViewModel()
                val articles = viewModel.newsByKeyword.collectAsLazyPagingItems()
                KeywordNewsScreen(
                    articles = articles,
                    event = viewModel::onEvent,
                    args = args,
                    onItemClicked = { newsItem ->
                        val newsArg = Json.encodeToString(Article.serializer(), newsItem)
                        navigateToDetail(navController) {
                            Route.NewsDetail(newsArg)
                        }
                    },
                    onBackPressed = { navController.navigateUp() }
                )
            }
            composable<Route.NewsDetail> {
                val viewModel: NewsDetailViewModel = hiltViewModel()
                val args = it.toRoute<Route.NewsDetail>()
                val newsItem = Json.decodeFromString(Article.serializer(), args.newsItem)
                val isFavoriteArticle by viewModel.isArticleFavorite.collectAsStateWithLifecycle()
                NewsDetailScreen(
                    article = newsItem,
                    isFavoriteArticle = isFavoriteArticle,
                    event = viewModel::onEvent,
                    onBackPressed = { navController.navigateUp() }
                )
            }
            composable<Route.SearchNews> {
                val viewModel: SearchNewsViewModel = hiltViewModel()
                val articles = viewModel.articles.collectAsLazyPagingItems()
                SearchNewsScreen(
                    articles = articles,
                    event = viewModel::onEvent,
                    onItemClicked = { article ->
                        val newsArg = Json.encodeToString(Article.serializer(), article)
                        navigateToDetail(navController) {
                            Route.NewsDetail(newsArg)
                        }
                    }
                )
            }
            composable<Route.Favorites> {
                val viewModel: FavoritesViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val searchText by viewModel.searchText.collectAsStateWithLifecycle()
                FavoritesScreen(
                    event = viewModel::onEvent,
                    searchText = searchText,
                    uiState = uiState,
                    onItemClicked = { article ->
                        val arg = Json.encodeToString(Article.serializer(), article)
                        navigateToDetail(navController) {
                            Route.NewsDetail(arg)
                        }
                    }
                )
            }
        }
    }
}

private fun navigateToDetail(navController: NavController, routeBuilder: () -> Route) {
    navController.navigate(routeBuilder()) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            launchSingleTop = true
            restoreState = true
        }
    }
}

private fun navigateToTab(navController: NavController, route: Route) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}