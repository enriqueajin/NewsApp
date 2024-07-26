package com.enriqueajin.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.NewsApiClient
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(private val api: NewsApiClient): NewsRepository {

    override fun getNews(
        category: String,
        pageSize: Int,
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = pageSize, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    category = category,
                    needsPagination = needsPagination
                )
            }).flow
    }

    override fun getNewsByKeyword(
        keyword: String,
        pageSize: Int,
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = pageSize, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
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