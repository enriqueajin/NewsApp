package com.enriqueajin.newsapp.domain.repository

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getArticlesByCategory(category: String): Flow<List<NewsItem>>

    fun getPagingArticlesByCategory(category: String): Flow<PagingData<NewsItem>>

    fun getArticlesByKeyword(keyword: String): Flow<List<NewsItem>>

    fun getPagingArticlesByKeyword(keyword: String): Flow<PagingData<NewsItem>>
}