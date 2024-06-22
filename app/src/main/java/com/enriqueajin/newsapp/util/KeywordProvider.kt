package com.enriqueajin.newsapp.util

object KeywordProvider {
    fun getRandomKeyword(): String {
        val index = (keywordList.indices).random()
        return keywordList[index]
    }

    private val keywordList = listOf(
        "Real Madrid",
        "NASA",
        "Productivity",
        "Microsoft",
        "Apple",
        "UEFA",
        "FIFA",
        "Movies",
        "Nature",
        "Recipes"
    )
}