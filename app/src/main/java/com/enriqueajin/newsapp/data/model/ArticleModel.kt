package com.enriqueajin.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ArticleModel(
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("category") val category: String? = null
)
