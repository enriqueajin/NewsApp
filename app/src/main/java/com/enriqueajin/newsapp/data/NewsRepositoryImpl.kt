package com.enriqueajin.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.network.NewsApiClient
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.repository.NewsRepository
import com.enriqueajin.newsapp.util.Constants.ALL_NEWS_PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.PREFETCH_ITEMS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(private val api: NewsApiClient): NewsRepository {

    override fun getNews(
        category: String,
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        val pageSize = if (needsPagination) PAGE_SIZE else ALL_NEWS_PAGE_SIZE
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
        needsPagination: Boolean
    ): Flow<PagingData<NewsItem>> {
        val pageSize = if (needsPagination) PAGE_SIZE else ALL_NEWS_PAGE_SIZE
        return Pager(config = PagingConfig(pageSize = pageSize, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    keyword = keyword,
                    needsPagination = needsPagination
                )
            }).flow
    }
}