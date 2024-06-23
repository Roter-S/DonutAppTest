package com.example.donutapptest.data

import retrofit2.Call
import retrofit2.http.GET

interface DonutApi {
    @GET("/donuts")
    fun getDonuts(): Call<List<Donut>>
}
