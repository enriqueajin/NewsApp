package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) {
    operator fun invoke(category: String = "general", pageSize: Int, needsPagination: Boolean = false):Flow<PagingData<NewsItem>> {
        return newsRepositoryImpl.getNews(category, pageSize, needsPagination)
    }
}