package com.enriqueajin.newsapp.domain

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.data.network.model.NewsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(keyword: String, pageSize: Int, needsPagination: Boolean = false): Flow<PagingData<NewsItem>> {
        return repository.getNewsByKeyword(keyword, pageSize, needsPagination)
    }
}
