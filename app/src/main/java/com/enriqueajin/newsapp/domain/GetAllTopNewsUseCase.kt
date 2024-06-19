package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.ui.model.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTopNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: String = "general", pageSize: String):Flow<List<NewsItem>> = flow {
        val news = newsRepository.getNews(category, pageSize).articles
        emit(news)
    }
}
