package com.enriqueajin.newsapp.data

import android.util.Log
import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.database.dao.ArticleDAO
import com.enriqueajin.newsapp.database.entities.ArticleEntity
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.model.toDomain
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsService,
    private val articleDAO: ArticleDAO
) {

    suspend fun getGeneralNewsFromApi(): List<Article> {
        Log.d("TAG", "getGeneralNewsFromApi: getting from API")
        val response = api.getGeneralNews()
        return response.map { it.toDomain() }
    }

    suspend fun getGeneralNewsFromDatabase(): List<Article> {
        Log.d("TAG", "getGeneralNewsFromApi: getting from database")
        val response = articleDAO.getNewsByCategory("general")
        return response.map { it.toDomain() }
    }

    suspend fun insertAllNews(articles: List<ArticleEntity>) {
        articleDAO.insertAllNews(articles)
    }

    suspend fun insertArticleToBookmarks(article: ArticleEntity) {
        articleDAO.insertArticleToBookmarks(article)
    }

    suspend fun clearNews() {
        articleDAO.deleteAllNews()
    }
}