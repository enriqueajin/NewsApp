package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.ui.model.NewsItem
import com.enriqueajin.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTopNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke():Flow<Resource<List<NewsItem>>> = flow {
        try {
            emit(Resource.Loading())
            val news = newsRepository.getAllTopNews().articles
            emit(Resource.Success(news))
        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred: $e"))
        }
    }
}
