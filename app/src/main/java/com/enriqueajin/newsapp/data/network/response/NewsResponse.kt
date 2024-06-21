package com.enriqueajin.newsapp.data.network.response

import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsItem>
)