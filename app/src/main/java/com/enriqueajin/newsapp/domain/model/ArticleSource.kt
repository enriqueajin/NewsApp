package com.enriqueajin.newsapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleSource(
    val id: String?,
    val name: String?,
)
