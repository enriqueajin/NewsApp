package com.enriqueajin.newsapp.presentation.search_news

sealed interface SearchNewsEvent {

    data class OnQueryNews(val query: String) : SearchNewsEvent
}