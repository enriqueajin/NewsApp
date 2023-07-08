package com.enriqueajin.newsapp.domain.model

import com.enriqueajin.newsapp.data.model.ArticleModel
import com.enriqueajin.newsapp.database.entities.ArticleEntity

data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val category: String? = null
)

fun ArticleEntity.toDomain() = Article(author, title, description, url, urlToImage, publishedAt)
fun ArticleModel.toDomain() = Article(author, title, description, url, urlToImage, publishedAt)