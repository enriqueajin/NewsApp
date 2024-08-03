package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepositoryImpl
) {
    operator fun invoke(keyword: String): Flow<PagingData<NewsItem>> {
        return repository.getPagingArticlesByKeyword(keyword)
    }

    fun getArticlesByKeyword(keyword: String):Flow<List<NewsItem>> {
        return repository.getArticlesByKeyword(keyword)
    }
}
