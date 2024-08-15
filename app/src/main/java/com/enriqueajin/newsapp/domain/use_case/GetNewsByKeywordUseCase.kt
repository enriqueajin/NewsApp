package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByKeywordUseCase @Inject constructor(
    private val repository: NewsRepositoryImpl
) {
    operator fun invoke(keyword: String): Flow<PagingData<Article>> {
        return repository.getPagingArticlesByKeyword(keyword)
    }

    fun getArticlesByKeyword(keyword: String):Flow<List<Article>> {
        return repository.getArticlesByKeyword(keyword)
    }
}
