package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.data.model.ArticleModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsService @Inject constructor(private val api: NewsApiClient) {

    suspend fun getGeneralNews(): List<ArticleModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getGeneralNews()
            response.body()?.articles ?: emptyList()
        }
    }
}