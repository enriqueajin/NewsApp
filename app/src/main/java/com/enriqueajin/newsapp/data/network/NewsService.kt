package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.data.response.NewsResponse
import javax.inject.Inject

class NewsService @Inject constructor(private val api: NewsApiClient) {
    suspend fun getAllTopNews(): NewsResponse = api.getAllTopNews()
    suspend fun getNewsByKeyword(keyword: String): NewsResponse = api.getNewsByKeyword(keyword)
}