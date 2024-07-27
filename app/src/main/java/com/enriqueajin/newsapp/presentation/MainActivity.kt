package com.enriqueajin.newsapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.presentation.home.HomeScreen
import com.enriqueajin.newsapp.presentation.home.HomeViewModel
import com.enriqueajin.newsapp.presentation.home.components.BottomNav
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsScreen
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsViewModel
import com.enriqueajin.newsapp.presentation.news_detail.NewsDetailScreen
import com.enriqueajin.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val keywordNewsViewModel: KeywordNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = { BottomNav(homeViewModel) }) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Home,
                            Modifier.padding(it)
                        ) {
                            composable<Home> {
                                HomeScreen(
                                    homeViewModel = homeViewModel,
                                    onSeeAllClicked = { news, keyword ->
                                        val newsArg = Json.encodeToString(
                                            serializer = ListSerializer(NewsItem.serializer()),
                                            value = news
                                        )
                                        navController.navigate(KeywordNews(newsArg, keyword))
                                    },
                                    onItemClicked = { newsItem ->
                                        Log.i("TAG", "NewsItem value $newsItem")
                                        val newsArg = Json.encodeToString(NewsItem.serializer(), newsItem)

                                        navController.navigate(NewsDetail(newsArg))
                                    }
                                )
                            }
                            composable<KeywordNews> {
                                val args = it.toRoute<KeywordNews>()

                                KeywordNewsScreen(
                                    keywordNewsViewModel = keywordNewsViewModel,
                                    args = args,
                                    onItemClicked = { newsItem ->
                                        Log.i("TAG", "NewsItem value $newsItem")
                                        val newsArg = Json.encodeToString(NewsItem.serializer(), newsItem)
                                        navController.navigate(NewsDetail(newsArg))
                                    },
                                    onBackPressed = { navController.navigateUp() }
                                )
                            }
                            composable<NewsDetail> {
                                val args = it.toRoute<NewsDetail>()
                                val newsItem = Json.decodeFromString(NewsItem.serializer(), args.newsItem)
                                NewsDetailScreen(
                                    newsItem = newsItem,
                                    onBackPressed = { navController.navigateUp() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}