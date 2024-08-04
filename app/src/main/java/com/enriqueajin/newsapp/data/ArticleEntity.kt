package com.enriqueajin.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enriqueajin.newsapp.domain.model.Article

@Entity
data class ArticleEntity (
    @PrimaryKey
    val id: Int,
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

fun ArticleEntity.toArticle(): Article {
    return Article(
        source = source,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}