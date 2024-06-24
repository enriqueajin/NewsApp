package com.enriqueajin.newsapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    val source: NewsSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

@Serializable
data class NewsSource(
    val id: String?,
    val name: String?,
)
