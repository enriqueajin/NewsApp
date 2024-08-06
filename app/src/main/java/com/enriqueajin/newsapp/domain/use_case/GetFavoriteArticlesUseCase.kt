package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteArticlesUseCase @Inject constructor(private val repository: NewsRepositoryImpl) {

    operator fun invoke(): Flow<List<Article>> = repository.getFavorites()
}