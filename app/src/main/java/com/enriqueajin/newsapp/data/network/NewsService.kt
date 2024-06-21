package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.data.network.response.NewsResponse
import javax.inject.Inject

class NewsService @Inject constructor(private val api: NewsApiClient) {
    suspend fun getNews(category: String, pageSize: String): NewsResponse = api.getNews(category, pageSize)
    suspend fun getNewsByKeyword(keyword: String): NewsResponse = api.getNewsByKeyword(keyword)
}