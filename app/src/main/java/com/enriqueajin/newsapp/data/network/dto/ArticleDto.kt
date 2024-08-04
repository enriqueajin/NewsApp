package com.enriqueajin.newsapp.data.network.dto

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.model.ArticleSource

data class ArticleDto(
    val source: ArticleSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

fun ArticleDto.toArticle(): Article {
    return Article(
        source = source?.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}