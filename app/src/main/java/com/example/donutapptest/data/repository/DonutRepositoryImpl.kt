package com.example.donutapptest.data.repository

import com.example.donutapptest.data.remote.ApiService
import com.example.donutapptest.domain.model.Donut
import javax.inject.Inject

class DonutRepositoryImpl @Inject constructor(private val apiService: ApiService) : DonutRepository {
    override suspend fun getDonuts(page: Int, pageSize: Int): List<Donut> {
        val response = apiService.getAllDonuts()
        if (response.isSuccessful) {
            val donuts = response.body() ?: emptyList()
            val fromIndex = (page - 1) * pageSize
            val toIndex = (fromIndex + pageSize).coerceAtMost(donuts.size)
            if (fromIndex >= donuts.size) return emptyList()
            return donuts.subList(fromIndex, toIndex)
        } else {
            return emptyList()
        }
    }
} 