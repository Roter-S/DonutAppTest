package com.example.donutapptest.data.remote

import com.example.donutapptest.data.remote.dto.DonutDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("donuts/all")
    suspend fun getAllDonuts(): Response<List<DonutDto>>
}