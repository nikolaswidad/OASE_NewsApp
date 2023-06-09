package com.nikolaswidad.oasesimpleapp.data.remote.retrofit

import com.nikolaswidad.oasesimpleapp.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : NewsResponse
}