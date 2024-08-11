package com.enriqueajin.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enriqueajin.newsapp.domain.model.Article

@Entity
data class ArticleEntity (
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

fun ArticleEntity.toDomain(): Article {
    return Article(
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