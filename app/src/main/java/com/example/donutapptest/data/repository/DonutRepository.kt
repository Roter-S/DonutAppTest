package com.example.donutapptest.data.repository

import com.example.donutapptest.data.model.Donut

interface DonutRepository {
    suspend fun getDonuts(): List<Donut>
} 