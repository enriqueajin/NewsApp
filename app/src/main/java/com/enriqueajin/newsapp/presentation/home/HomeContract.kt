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
        data class OnItemClick(val article: Article): Event()
        data class OnSeeAllClick(val keyword: String): Event()
    }

    data class State(
        val loading: Boolean = true,
        val error: String = "",
        val latestArticles: List<Article>? = null,
        val articlesByKeyword: List<Article>? = null,
        val newsByCategory: Flow<PagingData<Article>>? = null,
        val keyword: String = "",
        val category: String = CATEGORIES_INITIAL_VALUE,
    )

    sealed class Effect {
        data class NavigateToArticleDetail(val article: Article): Effect()
        data class NavigateToArticlesWithKeyword(val keyword: String): Effect()
    }
}

