package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.util.KeywordProvider

data class HomeInternalState (
    val category: String = "All",
    val keyword: String = KeywordProvider.getRandomKeyword(),
    val scrollPosition: Int = 0
)