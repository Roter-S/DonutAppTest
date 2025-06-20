package com.example.donutapptest.domain.model

import com.google.gson.annotations.SerializedName

data class Donut(
    val id: String,
    val type: String,
    val name: String,
    val ppu: Double,
    @SerializedName("image_url")
    val imageUrl: String,
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