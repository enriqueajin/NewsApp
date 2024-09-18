package com.enriqueajin.newsapp.domain.use_case

import androidx.paging.PagingData
import com.enriqueajin.newsapp.data.repository.NewsRepositoryImpl
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedNewsByCategoryUseCase @Inject constructor(
    private val newsRepositoryImpl: NewsRepositoryImpl
) {

    operator fun invoke(category: String = "general"): Flow<PagingData<Article>> {
        return newsRepositoryImpl.getPagingArticlesByCategory(category)
    }
}