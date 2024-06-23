package com.example.donutapptest.data

data class Donut(
    val id: String,
    val type: String,
    val name: String,
    val ppu: Double,
    val batters: Batters,
    val topping: List<Topping>
)

data class Batters(
    val batter: List<Batter>
)

data class Batter(
    val id: String,
    val type: String
)

data class Topping(
    val id: String,
    val type: String
)
