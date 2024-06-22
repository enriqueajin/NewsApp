package com.enriqueajin.newsapp.ui

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class KeywordNews(val news: String, val keyword: String)