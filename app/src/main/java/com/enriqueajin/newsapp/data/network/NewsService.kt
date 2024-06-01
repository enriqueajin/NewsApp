package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.ui.model.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor(private val api: NewsApiClient) {
    suspend fun getAllTopNews(): List<NewsItem> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllTopNews()
            response.body()?.articles ?: emptyList()
        }
    }
}