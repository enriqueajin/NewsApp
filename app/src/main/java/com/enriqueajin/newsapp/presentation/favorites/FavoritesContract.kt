package com.enriqueajin.newsapp.presentation.favorites

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.StateContract

interface FavoritesContract {

    data class State(
        override val loading: Boolean,
        override val error: String,
        val articles: List<Article>,
        val searchText: String,
    ): StateContract {
        companion object {
            fun EMPTY() = State(
                loading = true,
                error = "",
                articles = emptyList(),
                searchText = "",
            )
        }
    }

    sealed class UiEvent {
        data class OnSearchTextChange(val text: String): UiEvent()
        data object OnBackPressed: UiEvent()
        data class OnItemClick(val article: Article): UiEvent()
    }

    sealed class InternalEvent {
        data object OnInit: InternalEvent()
    }

    sealed class Effect {
        data object NavigateBack: Effect()
        data class NavigateToArticleDetail(val article: Article): Effect()
    }
}
