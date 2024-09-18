package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.model.toData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddArticleToFavoritesUseCase @Inject constructor(private val repository: NewsRepositoryImpl) {

    suspend operator fun invoke(article: Article): Flow<Result<Unit, DataError.Database>> {
        return repository.addFavoriteArticle(article.toData())
    }
}