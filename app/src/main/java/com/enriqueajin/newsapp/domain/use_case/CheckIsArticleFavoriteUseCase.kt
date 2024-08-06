package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsArticleFavoriteUseCase @Inject constructor(
    private val repository: NewsRepositoryImpl
) {

    operator fun invoke(articleId: String): Flow<Boolean> {
        return repository.checkIsArticleFavorite(articleId)
    }
}