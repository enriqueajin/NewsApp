package com.enriqueajin.newsapp.domain.use_case

import com.enriqueajin.newsapp.data.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.model.toData
import javax.inject.Inject

class DeleteArticleFromFavoritesUseCase @Inject constructor(private val repository: NewsRepositoryImpl) {

    suspend operator fun invoke(article: Article) {
        repository.deleteFavoriteArticle(article.toData())
    }
}