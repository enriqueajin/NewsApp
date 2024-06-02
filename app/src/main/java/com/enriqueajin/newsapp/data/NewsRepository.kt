package com.enriqueajin.newsapp.data

import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.ui.model.NewsItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {
    suspend fun getAllTopNews(): List<NewsItem> {
        return newsService.getAllTopNews()
    }

    suspend fun getNewsByKeyword(keyword: String): List<NewsItem> {
        return newsService.getNewsByKeyword(keyword)
    }
}