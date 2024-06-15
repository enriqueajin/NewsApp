package com.enriqueajin.newsapp.data

import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.data.response.NewsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {
    suspend fun getAllTopNews(): NewsResponse = newsService.getAllTopNews()
    suspend fun getNewsByKeyword(keyword: String): NewsResponse = newsService.getNewsByKeyword(keyword)
}