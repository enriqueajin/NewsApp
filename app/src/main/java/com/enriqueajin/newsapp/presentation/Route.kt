package com.enriqueajin.newsapp.presentation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    object Home: Route

    @Serializable
    object SearchNews: Route

    @Serializable
    object Favorites: Route

    @Serializable
    data class KeywordNews(val keyword: String): Route

    @Serializable
    data class NewsDetail(val newsItem: String): Route
}
