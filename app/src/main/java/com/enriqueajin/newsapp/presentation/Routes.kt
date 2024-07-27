package com.enriqueajin.newsapp.presentation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class KeywordNews(val news: String, val keyword: String)

@Serializable
data class NewsDetail(val newsItem: String)