package com.enriqueajin.newsapp.data.response

import com.enriqueajin.newsapp.data.model.ArticleModel
import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleModel>,
)
