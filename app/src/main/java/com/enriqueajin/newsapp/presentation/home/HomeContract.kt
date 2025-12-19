package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.UiText
import com.enriqueajin.newsapp.util.Constants.CATEGORIES_INITIAL_VALUE

interface HomeContract {

    sealed class Event {
        data class OnCategoryChange(val category: String): Event()
        data class OnKeywordChange(val keyword: String): Event()
        data class OnItemClick(val article: Article): Event()
        data class OnSeeAllClick(val keyword: String): Event()
        data object OnRetry: Event()
    }

    data class State(
        val loading: Boolean = true,
        val error: UiText = UiText.DynamicString(""),
        val latestArticles: List<Article> = emptyList(),
        val articlesByKeyword: List<Article> = emptyList(),
        val keyword: String = "",
        val category: String = CATEGORIES_INITIAL_VALUE,
    )

    sealed class InternalEvent {
        data object OnInit: InternalEvent()
    }

    sealed class Effect {
        data class NavigateToArticleDetail(val article: Article): Effect()
        data class NavigateToArticlesWithKeyword(val keyword: String): Effect()
    }
}
