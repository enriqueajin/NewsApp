package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.data.network.response.NewsResponse
import javax.inject.Inject

class NewsService @Inject constructor(private val api: NewsApiClient) {
    suspend fun getNews(
        category: String,
        page: Int,
        pageSize: Int
    ): NewsResponse = api.getNews(category, page, pageSize)

    suspend fun getNewsByKeyword(
        keyword: String,
        page: Int,
        pageSize: Int
    ): NewsResponse = api.getNewsByKeyword(keyword, page, pageSize)
}