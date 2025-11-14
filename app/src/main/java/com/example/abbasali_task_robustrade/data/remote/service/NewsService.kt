package com.example.abbasali_task_robustrade.data.remote.service

import com.example.abbasali_task_robustrade.data.remote.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("search")
    suspend fun getNews(
        @Query("q") query: String = "Google",
        @Query("lang") lang: String = "en",
        @Query("max") max: Int = 25,
        @Query("apikey") apiKey: String = "344032da6408e29cffe1ae6a8302038f"
    ): Response<ResponseModel>
}