package com.enriqueajin.newsapp.domain

import com.enriqueajin.newsapp.data.NewsRepository
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(keyword: String) = repository.getNewsByKeyword(keyword)
}