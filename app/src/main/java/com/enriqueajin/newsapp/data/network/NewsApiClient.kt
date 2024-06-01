package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.BuildConfig
import com.enriqueajin.newsapp.data.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiClient {

    @GET("top-headlines?" +
            "apiKey=${BuildConfig.NEWS_API_KEY}&" +
            "language=en&" +
            "sortBy=publishedAt&" +
            "category=general")
    suspend fun getAllTopNews(): Response<NewsResponse>
}