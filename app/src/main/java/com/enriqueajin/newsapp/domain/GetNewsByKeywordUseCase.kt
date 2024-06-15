package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.ui.model.NewsItem
import com.enriqueajin.newsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(keyword: String): Flow<Resource<List<NewsItem>>> = flow {
        try {
            emit(Resource.Loading())
            val news = repository.getNewsByKeyword(keyword).articles
            emit(Resource.Success(news))

        } catch (e: Exception) {
            emit(Resource.Error("The following error occurred: $e"))
        }
    }
}
