package com.enriqueajin.newsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.enriqueajin.newsapp.data.network.NewsService
import com.enriqueajin.newsapp.data.network.model.NewsItem
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
     private val service: NewsService,
     private val needsPagination: Boolean,
     private val keyword: String? = null,
     private val category: String? = null,
):
     PagingSource<Int, NewsItem>() {

     override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
          return state.anchorPosition
     }

     override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
          return try {
               val page = params.key ?: 1
               val pageSize = 20
               val prevKey: Int?
               val nextKey: Int?
               val response = when {
                    keyword != null -> service.getNewsByKeyword(keyword, page, pageSize)
                    else -> service.getNews(category ?: "", page, pageSize)
               }
               val news = response.articles

               if (needsPagination) {
                    prevKey = if (page > 1) page - 1 else null
                    nextKey = if (news.size == pageSize) page + 1 else null
               } else {
                    prevKey = null
                    nextKey = null
               }

               LoadResult.Page(
                    data = news.filter { it.title != "[Removed]" },
                    prevKey = prevKey,
                    nextKey = nextKey
               )
          } catch (e: Exception) {
               e.printStackTrace()
               LoadResult.Error(e)
          }
     }
}
