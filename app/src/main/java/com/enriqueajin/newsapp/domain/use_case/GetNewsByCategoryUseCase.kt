package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) {
    operator fun invoke(category: String = "general"):Flow<PagingData<NewsItem>> {
        return newsRepositoryImpl.getPagingArticlesByCategory(category)
    }

    fun getArticlesByCategory(category: String = "general"):Flow<List<NewsItem>> {
        return newsRepositoryImpl.getArticlesByCategory(category)
    }
}