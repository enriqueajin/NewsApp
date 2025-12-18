package com.enriqueajin.newsapp.domain.repository

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.local.ArticleEntity
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getFixedSizeNewsByCategory(category: String): Result<List<Article>, DataError.Network>

    suspend fun getFixedSizeNewsByKeyword(keyword: String): Result<List<Article>, DataError.Network>

    fun getPagingArticlesByCategory(category: String): Flow<PagingData<Article>>

    fun getPagingArticlesByKeyword(keyword: String): Flow<PagingData<Article>>

    fun getFavorites(): Flow<List<Article>>

    suspend fun addFavoriteArticle(article: ArticleEntity)

    suspend fun deleteFavoriteArticle(article: ArticleEntity)

    fun checkIsArticleFavorite(articleId: String): Flow<Boolean>
}