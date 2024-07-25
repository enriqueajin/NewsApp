package com.enriqueajin.newsapp.domain

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String = "general", pageSize: Int, needsPagination: Boolean = false):Flow<PagingData<NewsItem>> {
        return newsRepository.getNews(category, pageSize, needsPagination)
    }
}