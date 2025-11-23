package com.enriqueajin.newsapp.presentation.nav_graph

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val hasTopBar: Boolean) {

    @Serializable
    data object Home: Route(false)

    @Serializable
    data object SearchNews: Route(false)

    @Serializable
    data object Favorites: Route(false)

    @Serializable
    data class KeywordNews(val keyword: String): Route(true)

    @Serializable
    data class NewsDetail(val article: String): Route(true)

    companion object {
        fun NavBackStackEntry.getRoute(): Route {
            return when {
                destination.hasRoute(Home::class) -> toRoute<Home>()
                destination.hasRoute(SearchNews::class) -> toRoute<SearchNews>()
                destination.hasRoute(Favorites::class) -> toRoute<Favorites>()
                destination.hasRoute(KeywordNews::class) -> toRoute<KeywordNews>()
                destination.hasRoute(NewsDetail::class) -> toRoute<NewsDetail>()
                else -> toRoute<Home>()
            }
        }

        private fun Route.getClassName() = this::class.qualifiedName
    }
}
