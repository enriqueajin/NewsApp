package com.enriqueajin.newsapp.presentation.article_detail

import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.StateContract

interface ArticleDetailContract {

    data class State(
        override val loading: Boolean,
        override val error: String,
        val isFavorite: Boolean,
        val article: Article,
    ) : StateContract {
        companion object {
            fun EMPTY(article: Article) = State(
                loading = false,
                error = "",
                isFavorite = false,
                article = article,
            )
        }
    }

    sealed class UiEvent() {
        data object OnBackPressed: UiEvent()
        data class OnShareIconClick(val url: String): UiEvent()
        data object OnFavoriteIconClick: UiEvent()
    }

    sealed class InternalEvent() {
        data object OnInit: InternalEvent()
    }

    sealed class Effect() {
        data object NavigateBack: Effect()
        data class ShareArticleUrl(val url: String): Effect()
    }
}