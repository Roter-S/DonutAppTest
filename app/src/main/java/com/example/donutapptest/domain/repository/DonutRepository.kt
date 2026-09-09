package com.example.donutapptest.domain.repository

import com.example.donutapptest.core.common.Result
import com.example.donutapptest.domain.model.Donut

interface DonutRepository {
    suspend fun getDonuts(): Result<List<Donut>>
}
