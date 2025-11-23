package com.enriqueajin.newsapp.presentation.nav_graph

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailViewModel
import com.enriqueajin.newsapp.presentation.article_detail.components.NewsDetailsTopBar
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsContract.UiEvent
import com.enriqueajin.newsapp.presentation.keyword_news.KeywordNewsViewModel
import com.enriqueajin.newsapp.presentation.keyword_news.components.KeywordNewsTopBarApp
import com.enriqueajin.newsapp.presentation.nav_graph.Route.Companion.getRoute
import kotlinx.serialization.json.Json

@Composable
fun AppTopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.getRoute()

    currentRoute?.hasTopBar?.let { hasTopBar ->
        if (hasTopBar) {
            when (currentRoute) {
                is Route.KeywordNews -> {
                    val viewModel = hiltViewModel<KeywordNewsViewModel>()
                    KeywordNewsTopBarApp(
                        title = currentRoute.keyword,
                        onBackPressed = { viewModel.onPushEvent(UiEvent.OnBackPressed) }
                    )
                }
                is Route.NewsDetail -> {
                    val viewModel = hiltViewModel<ArticleDetailViewModel>()
                    val isFavorite by viewModel.isArticleFavorite.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    val article = Json.decodeFromString(Article.serializer(), currentRoute.article)
                    NewsDetailsTopBar(
                        isFavoriteArticle = isFavorite,
                        onShareArticle = { startShareIntent(article, context) },
                        onAddFavorite = {
                            viewModel.addArticleToFavorites(article)
                            viewModel.checkArticleFavorite(article.url)
                        },
                        onDeleteFavorite = {
                            viewModel.deleteArticleFromFavorites(article)
                            viewModel.checkArticleFavorite(article.url)
                        },
                        onBackPressed = { navController.navigateUp() }
                    )
                }
                else -> {}
            }
        }
    }
}

private fun startShareIntent(article: Article, context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, article.url)
        type = "text/plain"
    }
    context.startActivity(intent)
}
