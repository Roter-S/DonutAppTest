package com.example.donutapptest.domain.model

data class Donut(
    val id: String,
    val type: String,
    val name: String,
    val ppu: Double,
    val imageUrl: String,
    val batters: List<Batter>,
    val toppings: List<Topping>,
    val isFavorite: Boolean = false
)

data class Batter(
    val id: String,
    val type: String
)

data class Topping(
    val id: String,
    val type: String
)
