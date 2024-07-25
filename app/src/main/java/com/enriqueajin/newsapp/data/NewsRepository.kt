package com.enriqueajin.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getNews(
        category: String,
        pageSize: Int,
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = pageSize, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    service = newsService,
                    category = category,
                    needsPagination = needsPagination
                )
            }).flow
    }

    fun getNewsByKeyword(
        keyword: String,
        pageSize: Int,
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = pageSize, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    service = newsService,
                    keyword = keyword,
                    needsPagination = needsPagination
                )
            }).flow
    }

    companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 3
    }
}