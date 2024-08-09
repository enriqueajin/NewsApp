package com.enriqueajin.newsapp.presentation.nav_graph

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home: Route

    @Serializable
    data object SearchNews: Route

    @Serializable
    data object Favorites: Route

    @Serializable
    data class KeywordNews(val keyword: String): Route

    @Serializable
    data class NewsDetail(val newsItem: String): Route
}
