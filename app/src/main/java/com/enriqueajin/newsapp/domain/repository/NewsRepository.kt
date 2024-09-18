package com.enriqueajin.newsapp.domain.repository

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.local.ArticleEntity
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getArticlesByCategory(category: String): Flow<Result<List<Article>, DataError.Network>>

    fun getPagingArticlesByCategory(category: String): Flow<PagingData<Article>>

    suspend fun getArticlesByKeyword(keyword: String): Flow<Result<List<Article>, DataError.Network>>

    fun getPagingArticlesByKeyword(keyword: String): Flow<PagingData<Article>>

    fun getFavorites(): Flow<Result<List<Article>, DataError.Database>>

    suspend fun addFavoriteArticle(article: ArticleEntity): Flow<Result<Unit, DataError.Database>>

    suspend fun deleteFavoriteArticle(article: ArticleEntity): Flow<Result<Unit, DataError.Database>>

    fun checkIsArticleFavorite(articleId: String): Flow<Result<Boolean, DataError.Database>>
}