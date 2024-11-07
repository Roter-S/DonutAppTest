package com.example.donutapptest.data.remote

import com.example.donutapptest.data.remote.response.DonutResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("donuts")
    suspend fun getDonuts(): Response<DonutResponse>
}