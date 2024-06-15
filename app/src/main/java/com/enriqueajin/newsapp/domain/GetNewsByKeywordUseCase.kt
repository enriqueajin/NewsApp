package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.ui.model.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(keyword: String): Flow<List<NewsItem>> = flow {
        val news = repository.getNewsByKeyword(keyword).articles
        emit(news)
    }
}
