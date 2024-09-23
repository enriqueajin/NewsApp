package com.enriqueajin.newsapp.presentation.home

import com.enriqueajin.newsapp.util.KeywordProvider

data class HomeLocalState (
    val category: String = "All",
    val keyword: String = KeywordProvider.getRandomKeyword(),
    val categoriesScrollPosition: Int = 0,
)