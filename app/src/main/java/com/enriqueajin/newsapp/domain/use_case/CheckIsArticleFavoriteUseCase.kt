package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsArticleFavoriteUseCase @Inject constructor(
    private val repository: NewsRepositoryImpl
) {

    operator fun invoke(articleId: String): Flow<Result<Boolean, DataError.Database>> {
        return repository.checkIsArticleFavorite(articleId)
    }
}