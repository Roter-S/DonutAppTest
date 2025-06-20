package com.example.donutapptest.data.repository

import com.example.donutapptest.domain.model.Donut

interface DonutRepository {
    suspend fun getDonuts(page: Int, pageSize: Int): List<Donut>
} 