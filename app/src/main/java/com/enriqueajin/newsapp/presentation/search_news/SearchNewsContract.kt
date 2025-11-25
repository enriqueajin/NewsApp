package com.enriqueajin.newsapp.presentation.search_news

import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.StateContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface SearchNewsContract {

    data class State(
        override val loading: Boolean,
        override val error: String,
        val query: String,
        val searchedArticles: Flow<PagingData<Article>>,
    ): StateContract {
        companion object {
            fun EMPTY() = State(
                loading = false,
                error = "",
                query = "",
                searchedArticles = flowOf(PagingData.empty())
            )
        }
    }

    sealed class UiEvent {
        data class OnQueryChange(val query: String): UiEvent()
        data class OnItemClick(val article: Article): UiEvent()
    }

    sealed class InternalEvent

    sealed class Effect {
        data class NavigateToArticleDetail(val article: Article): Effect()
    }
}
