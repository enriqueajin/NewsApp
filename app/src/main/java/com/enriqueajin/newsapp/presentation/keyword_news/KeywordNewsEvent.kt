package com.enriqueajin.newsapp.presentation.keyword_news

sealed interface KeywordNewsEvent {

    data class GetArticlesByKeyword(val keyword: String): KeywordNewsEvent
}