package com.enriqueajin.newsapp.presentation.home

import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE
import kotlinx.coroutines.flow.Flow

interface HomeContract {

    sealed class Event {
        data object OnInit: Event()
        data class OnCategoryChange(val category: String): Event()
        data class OnKeywordChange(val keyword: String): Event()
    }

    sealed interface State {

        object Loading: State

        data class Success(
            val latestArticles: List<Article>? = null,
            val articlesByKeyword: List<Article>? = null,
            val newsByCategory: Flow<PagingData<Article>>? = null,
            val keyword: String = "",
            val category: String = CATEGORIES_INITIAL_VALUE,
        ): State

        data class Error(val throwable: Throwable): State
    }
}

