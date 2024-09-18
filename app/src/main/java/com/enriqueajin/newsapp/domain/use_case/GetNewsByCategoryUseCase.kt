package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) {

    operator fun invoke(category: String = "general"): Flow<Result<List<Article>, DataError.Network>>  {
        return newsRepositoryImpl.getArticlesByCategory(category)
    }
}