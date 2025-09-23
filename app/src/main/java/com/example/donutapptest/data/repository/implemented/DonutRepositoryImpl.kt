package com.example.donutapptest.data.repository.implemented

import com.example.donutapptest.data.remote.ApiService
import com.example.donutapptest.data.repository.DonutRepository
import com.example.donutapptest.domain.model.Donut
import javax.inject.Inject

class DonutRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    DonutRepository {
    override suspend fun getDonuts(): List<Donut> {
        val response = apiService.getAllDonuts()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}
