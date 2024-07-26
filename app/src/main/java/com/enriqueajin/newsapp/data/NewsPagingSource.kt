package com.enriqueajin.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enriqueajin.newsapp.data.network.NewsApiClient
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.util.Constants.ALL_NEWS_PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.REMOVED
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
     private val api: NewsApiClient,
     private val needsPagination: Boolean,
     private val keyword: String? = null,
     private val category: String? = null,
): PagingSource<Int, NewsItem>() {

     override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
          return state.anchorPosition
     }

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
          return try {
               val page = params.key ?: 1
               val pageSize = if (needsPagination) PAGE_SIZE else ALL_NEWS_PAGE_SIZE
               val prevKey: Int?
               val nextKey: Int?
               val response = when {
                    keyword != null -> api.getNewsByKeyword(keyword, page, pageSize)
                    else -> api.getNews(category ?: "", page, pageSize)
               }
               val news = response.articles

               if (needsPagination) {
                    prevKey = if (page > 1) page - 1 else null
                    nextKey = if (news.size == pageSize) page + 1 else null
               } else {
                    prevKey = null
                    nextKey = null
               }

               val data = news.filter { it.title != REMOVED }.filter { it.content != null}

               LoadResult.Page(
                    data = data ,
                    prevKey = prevKey,
                    nextKey = nextKey
               )
          } catch (e: Exception) {
               e.printStackTrace()
               LoadResult.Error(e)
          }
     }
}
