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
import com.enriqueajin.newsapp.util.Constants.REMOVED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(private val api: NewsApiClient): NewsRepository {

    /**
     * Get articles by category from Api for HomeScreen (without pagination)
     * @param category category for which articles will be sorted
     * @return Flow with the list of articles
     */
    override fun getArticlesByCategory(category: String): Flow<List<NewsItem>> = flow {
        val data = api.getArticlesByCategory(
            category = category,
            page = 1,
            pageSize = ALL_NEWS_PAGE_SIZE
        )
        val articles = data.articles
        val filteredArticles = articles.filter { it.title != REMOVED }
        emit(filteredArticles)
    }

    /**
     * Get articles by category from Api with pagination
     * @param category category for which articles will be sorted
     * @return Flow with the list of articles
     */
    override fun getPagingArticlesByCategory(category: String): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    category = category,
                )
            }).flow
    }

    /**
     * Get articles by keyword from Api for HomeScreen (without pagination)
     * @param keyword Keyword for which articles will be searched
     * @return Flow with the list of articles
     */
    override fun getArticlesByKeyword(keyword: String): Flow<List<NewsItem>> = flow {
        val data = api.getArticlesByKeyword(
            keyword = keyword,
            page = 1,
            pageSize = ALL_NEWS_PAGE_SIZE
        )
        val articles = data.articles
        val filteredArticles = articles.filter { it.title != REMOVED }
        emit(filteredArticles)
    }

    /**
     * Get articles by keyword from Api with pagination
     * @param keyword Keyword for which articles will be searched
     * @return Flow with the list of articles
     */
    override fun getPagingArticlesByKeyword(keyword: String, ): Flow<PagingData<NewsItem>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(
                    api = api,
                    keyword = keyword,
                )
            }).flow
    }
}