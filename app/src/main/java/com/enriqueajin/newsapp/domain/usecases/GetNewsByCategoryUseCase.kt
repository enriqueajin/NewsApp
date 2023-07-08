package com.enriqueajin.newsapp.domain.usecases

import com.enriqueajin.newsapp.data.NewsRepository
import com.enriqueajin.newsapp.database.entities.toDatabase
import com.enriqueajin.newsapp.domain.model.Article
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(category: String?): List<Article>? {
        val news = repository.getGeneralNewsFromApi()

        return if (news.isNotEmpty()) {
            repository.clearNews()
            repository.insertAllNews(news.map { it.toDatabase(category) })
            news
        } else {
            repository.getGeneralNewsFromDatabase()
        }
    }
}