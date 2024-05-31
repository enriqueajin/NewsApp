package com.enriqueajin.newsapp.ui.model

data class NewsItem(
    val source: NewsSource = NewsSource("",""),
    val author: String? = "",
    val title: String?,
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String?,
    val content: String = ""
)

data class NewsSource(
    val id: String?,
    val name: String?
)
