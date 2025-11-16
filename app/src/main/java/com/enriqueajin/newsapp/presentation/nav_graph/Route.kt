package com.enriqueajin.newsapp.presentation.nav_graph

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object Home: Route()

    @Serializable
    data object SearchNews: Route()

    @Serializable
    data object Favorites: Route()

    @Serializable
    data class KeywordNews(val keyword: String): Route()

    @Serializable
    data class NewsDetail(val article: String): Route()

    companion object {
        fun NavBackStackEntry.toRoute(): Route? {
            return when(destination.route) {
                Home.getClassName() -> Home
                SearchNews.getClassName() -> SearchNews
                Favorites.getClassName() -> Favorites
                else -> null
            }
        }

        private fun Route.getClassName() = this::class.qualifiedName
    }
}
