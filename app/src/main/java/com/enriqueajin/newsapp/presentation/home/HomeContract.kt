package com.enriqueajin.newsapp.presentation.home

import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.UiText
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface HomeContract {

    sealed class Event {
        data object OnInit: Event()
        data class OnCategoryChange(val category: String): Event()
        data class OnKeywordChange(val keyword: String): Event()
        data class OnItemClick(val article: Article): Event()
        data class OnSeeAllClick(val keyword: String): Event()
        data object OnRetryClick: Event()
    }

    data class State(
        val loading: Boolean,
        val error: UiText,
        val latestArticles: List<Article>,
        val articlesByKeyword: List<Article>,
        val newsByCategory: Flow<PagingData<Article>>,
        val keyword: String,
        val category: String,
    ) {

        companion object {
            fun empty() = State(
                loading = true,
                error = UiText.DynamicString(""),
                latestArticles = emptyList(),
                articlesByKeyword = emptyList(),
                newsByCategory = flowOf(PagingData.empty()),
                keyword = "",
                category = CATEGORIES_INITIAL_VALUE
            )
        }
    }

    sealed class Effect {
        data class NavigateToArticleDetail(val article: Article): Effect()
        data class NavigateToArticlesWithKeyword(val keyword: String): Effect()
    }
}

