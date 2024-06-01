package com.enriqueajin.newsapp.data.response

import com.enriqueajin.newsapp.ui.model.NewsItem
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<NewsItem>
)