package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.paging.PagingData
import com.enriqueajin.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed interface KeywordNewsContract {

    data class State(
        val loading: Boolean = true,
        val error: String = "",
        val keyword: String = "",
        val articles: Flow<PagingData<Article>> = flowOf(PagingData.empty())
    )

    sealed class UiEvent {
        data class OnItemClick(val article: Article): UiEvent()
        data object OnBackPressed: UiEvent()
    }

    sealed class InternalEvent {
        data object OnInit: InternalEvent()
    }

    sealed class Effect {
        data class NavigateToArticleDetail(val article: Article): Effect()
        data object NavigateBack: Effect()
    }
}
