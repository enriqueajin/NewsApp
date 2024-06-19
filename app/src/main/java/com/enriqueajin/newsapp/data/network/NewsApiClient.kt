package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.BuildConfig
import com.enriqueajin.newsapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiClient {

    @GET("top-headlines?" +
            "apiKey=${BuildConfig.NEWS_API_KEY}&" +
            "language=en&" +
            "sortBy=publishedAt&" +
            "category=general&" +
            "pageSize=10")
    suspend fun getAllTopNews(): NewsResponse

    @GET("everything?" +
            "apiKey=${BuildConfig.NEWS_API_KEY}&" +
            "searchIn=title&" +
            "language=en&" +
            "sortBy=publishedAt&" +
            "pageSize=15")
    suspend fun getNewsByKeyword(@Query("q") keyword: String): NewsResponse
}