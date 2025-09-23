package com.example.donutapptest.data.repository

import com.example.donutapptest.domain.model.Donut

interface DonutRepository {
    suspend fun getDonuts(): List<Donut>
} 