package com.example.donutapptest.data.remote.response

import com.example.donutapptest.domain.model.Donut

data class DonutResponse(
    val status: String,
    val donuts: List<Donut>
)