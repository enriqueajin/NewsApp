package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import javax.inject.Inject

class GetAllTopNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() = newsRepository.getAllTopNews()
}