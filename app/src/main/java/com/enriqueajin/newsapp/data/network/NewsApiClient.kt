package com.enriqueajin.newsapp.data.network

import com.enriqueajin.newsapp.data.model.ArticleModel
import com.enriqueajin.newsapp.data.response.NewsResponse
import com.enriqueajin.newsapp.data.utils.Constants.API_KEY
import com.enriqueajin.newsapp.data.utils.Constants.COUNTRY_NEWS
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiClient {

    @GET("top-headlines?" +
            "apiKey=${API_KEY}&" +
            "country=${COUNTRY_NEWS}&" +
            "category=general")
    suspend fun getGeneralNews(): Response<NewsResponse>
}