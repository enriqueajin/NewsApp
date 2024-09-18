package com.enriqueajin.newsapp.util

import com.enriqueajin.newsapp.data.network.dto.ArticleDto
import com.enriqueajin.newsapp.domain.model.ArticleSource

object FakeArticleProvider {

    fun getArticles(): List<ArticleDto> {
        return (0..9).map { i ->
            ArticleDto(
                source = ArticleSource(id = "$i", name = "Source $i"),
                author = "Author $i",
                title = if (i % 2 == 0) "[REMOVED]" else "Title $i",
                description = "Description $i",
                url = "https://example.com/$i",
                urlToImage = "https://example.com/image$i.jpg",
                publishedAt = "2024-09-03T10:00:00Z",
                content = "Content $i"
            )
        }
    }
}