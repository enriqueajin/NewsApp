package com.enriqueajin.newsapp.presentation.nav_graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailScreen
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailViewModel
import com.enriqueajin.newsapp.presentation.bottom_bar.BottomBar
import com.enriqueajin.newsapp.presentation.bottom_bar.BottomBarItem
import com.enriqueajin.newsapp.presentation.favorites.FavoritesScreen
import com.enriqueajin.newsapp.presentation.favorites.FavoritesViewModel
import com.enriqueajin.newsapp.presentation.home.HomeScreen
import com.enriqueajin.newsapp.presentation.home.HomeViewModel
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsScreen
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsViewModel
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsScreen
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsViewModel
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
        AnimatedVisibility(
            visible = isBottomBarVisible,
            enter = slideInVertically(animationSpec = tween(600)) { it },
            exit = slideOutVertically(animationSpec = tween(600)) { it }
        ) {
            BottomBar(
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
            modifier = Modifier.padding(it)
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
                        val article = Json.encodeToString(Article.serializer(), newsItem)
                        navigateToDetail(navController) {
                            Route.NewsDetail(article)
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
                        val article = Json.encodeToString(Article.serializer(), newsItem)
                        navigateToDetail(navController) {
                            Route.NewsDetail(article)
                        }
                    },
                    onBackPressed = { navController.navigateUp() }
                )
            }
            composable<Route.NewsDetail> {
                val viewModel: ArticleDetailViewModel = hiltViewModel()
                val args = it.toRoute<Route.NewsDetail>()
                val article = Json.decodeFromString(Article.serializer(), args.article)
                val isFavoriteArticle by viewModel.isArticleFavorite.collectAsStateWithLifecycle()
                ArticleDetailScreen(
                    article = article,
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
                        val item = Json.encodeToString(Article.serializer(), article)
                        navigateToDetail(navController) {
                            Route.NewsDetail(item)
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
                        val item = Json.encodeToString(Article.serializer(), article)
                        navigateToDetail(navController) {
                            Route.NewsDetail(item)
                        }
                    }
                )
            }
        }
    }
}

private fun navigateToDetail(navController: NavController, routeBuilder: () -> Route) {
    navController.navigate(routeBuilder()) {
        launchSingleTop = true
        restoreState = true
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