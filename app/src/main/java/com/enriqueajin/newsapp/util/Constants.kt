package com.enriqueajin.newsapp.util

object Constants {
    val CATEGORIES = listOf("All", "Science", "Technology", "Sports", "Health", "Business", "Entertainment")

    const val BASE_URL = "https://newsapi.org/v2/"

    // NewsItem default values
    const val NO_SOURCE_ID = "No source id"
    const val NO_SOURCE_NAME = "No source name"
    const val NO_AUTHOR = "No author"
    const val NO_TITLE = "No title"
    const val NO_DESCRIPTION = "No description"
    const val NO_URL = "No url"
    const val NO_PICTURE_URL = "No url to image"
    const val NO_DATE = "No date"
    const val NO_CONTENT = "No content"
    const val REMOVED = "[Removed]"

    const val HTTP_ERROR_UPGRADE_REQUIRED = "HTTP 426"

    // NewsPagingSource
    const val ALL_NEWS_PAGE_SIZE = 15
    const val PAGE_SIZE = 25
    const val PREFETCH_ITEMS = 3
}