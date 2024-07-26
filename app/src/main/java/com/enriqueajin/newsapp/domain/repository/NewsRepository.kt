package com.enriqueajin.newsapp.domain.repository

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(category: String, pageSize: Int, needsPagination: Boolean): Flow<PagingData<NewsItem>>

    fun getNewsByKeyword(keyword: String, pageSize: Int, needsPagination: Boolean): Flow<PagingData<NewsItem>>
}