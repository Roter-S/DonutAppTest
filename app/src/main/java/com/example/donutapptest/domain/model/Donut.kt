package com.example.donutapptest.domain.model

data class Donut(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val price: Double,
    val available: Boolean,
    val calories: Int,
    val image: String
)