package com.enriqueajin.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enriqueajin.newsapp.data.network.NewsApiClient
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.util.Constants.PAGE_SIZE
import com.enriqueajin.newsapp.util.Constants.REMOVED
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
     private val api: NewsApiClient,
     private val keyword: String? = null,
     private val category: String? = null,
): PagingSource<Int, NewsItem>() {

     override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
          return state.anchorPosition
     }

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
          return try {
               val page = params.key ?: 1
               val response = when {
                    keyword != null -> api.getArticlesByKeyword(keyword, page, PAGE_SIZE)
                    else -> api.getArticlesByCategory(category ?: "", page, PAGE_SIZE)
               }
               val news = response.articles

               val prevKey = if (page > 1) page - 1 else null
               val nextKey = if (news.size == PAGE_SIZE) page + 1 else null

               val data = news.filter { it.title != REMOVED }

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
