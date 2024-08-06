package com.enriqueajin.newsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.favorites.FavoritesScreen
import com.enriqueajin.newsapp.presentation.favorites.FavoritesViewModel
import com.enriqueajin.newsapp.presentation.home.HomeScreen
import com.enriqueajin.newsapp.presentation.home.HomeViewModel
import com.enriqueajin.newsapp.presentation.home.components.BottomNav
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsScreen
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsViewModel
import com.enriqueajin.newsapp.presentation.news_detail.NewsDetailScreen
import com.enriqueajin.newsapp.presentation.news_detail.NewsDetailViewModel
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsScreen
import com.enriqueajin.newsapp.presentation.search_news.SearchNewsViewModel
import com.enriqueajin.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = { BottomNav(navController, homeViewModel) }) {
                        NavHost(
                            navController = navController,
                            startDestination = Route.Home,
                            Modifier.padding(it)
                        ) {
                            composable<Route.Home> {
                                val localState = homeViewModel.localState.value
                                val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                                val articlesStateFlow = homeViewModel.newsByCategory

                                HomeScreen(
                                    event = homeViewModel::onEvent,
                                    localState = localState,
                                    uiState = uiState,
                                    articlesStateFlow = articlesStateFlow,
                                    onSeeAllClicked = { keyword ->
                                        navController.navigate(Route.KeywordNews(keyword))
                                    },
                                    onItemClicked = { newsItem ->
                                        val newsArg = Json.encodeToString(Article.serializer(), newsItem)

                                        navController.navigate(Route.NewsDetail(newsArg)) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                homeViewModel.setSelectedTabIndex(0)
                            }
                            composable<Route.KeywordNews> {
                                val args = it.toRoute<Route.KeywordNews>()
                                val keywordNewsViewModel: KeywordNewsViewModel by viewModels()
                                val articles = keywordNewsViewModel.newsByKeyword.collectAsLazyPagingItems()

                                KeywordNewsScreen(
                                    articles = articles,
                                    event = keywordNewsViewModel::onEvent,
                                    args = args,
                                    onItemClicked = { newsItem ->
                                        val newsArg = Json.encodeToString(Article.serializer(), newsItem)
                                        navController.navigate(Route.NewsDetail(newsArg)) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    onBackPressed = { navController.navigateUp() }
                                )
                            }
                            composable<Route.NewsDetail> {
                                val viewModel: NewsDetailViewModel by viewModels()
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
                                val searchNewsViewModel: SearchNewsViewModel by viewModels()
                                val articles = searchNewsViewModel.articles.collectAsLazyPagingItems()

                                SearchNewsScreen(
                                    articles = articles,
                                    event = searchNewsViewModel::onEvent,
                                    onItemClicked = { article ->
                                        val newsArg = Json.encodeToString(Article.serializer(), article)
                                        navController.navigate(Route.NewsDetail(newsArg)) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                homeViewModel.setSelectedTabIndex(1)
                            }
                            composable<Route.Favorites> {
                                val viewModel: FavoritesViewModel by viewModels()
                                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                                val searchText by viewModel.searchText.collectAsStateWithLifecycle()
                                FavoritesScreen(
                                    event = viewModel::onEvent,
                                    searchText = searchText,
                                    uiState = uiState,
                                    onItemClicked = { article ->
                                        val arg = Json.encodeToString(Article.serializer(), article)
                                        navController.navigate(Route.NewsDetail(arg)) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                                homeViewModel.setSelectedTabIndex(2)
                            }
                        }
                    }
                }
            }
        }
    }
}