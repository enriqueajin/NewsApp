package com.enriqueajin.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.enriqueajin.newsapp.ui.home.Home
import com.enriqueajin.newsapp.ui.home.tabs.news.NewsViewModel
import com.enriqueajin.newsapp.ui.keyword_news.KeywordNewsScreen
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Home
                    ) {
                        composable<Home> {
                            Home(newsViewModel) {
                                val news = Json.encodeToString(
                                    serializer = ListSerializer(NewsItem.serializer()),
                                    value = it
                                )
                                navController.navigate(KeywordNews(news))
                            }
                        }
                        composable<KeywordNews> {
                            val args = it.toRoute<KeywordNews>()
                            val news = Json.decodeFromString(
                                deserializer = ListSerializer(NewsItem.serializer()),
                                string = args.news
                            )
                            KeywordNewsScreen(
                                news = news,
                                onItemClicked = {},
                                onBackPressed = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}