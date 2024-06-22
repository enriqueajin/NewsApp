package com.enriqueajin.newsapp.data

import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.data.network.response.NewsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {
    suspend fun getNews(category: String, pageSize: String): NewsResponse = newsService.getNews(category, pageSize)
    suspend fun getNewsByKeyword(keyword: String, pageSize: String): NewsResponse = newsService.getNewsByKeyword(keyword, pageSize)
}