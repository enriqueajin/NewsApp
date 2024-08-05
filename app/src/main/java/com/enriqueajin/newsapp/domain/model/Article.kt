package com.enriqueajin.newsapp.domain.model

import com.enriqueajin.newsapp.data.ArticleEntity
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

fun Article.toData(): ArticleEntity {
    return ArticleEntity(
        source = source,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}