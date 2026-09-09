package com.example.donutapptest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DonutDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("ppu") val ppu: Double?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("batters") val batters: BattersDto?,
    @SerializedName("topping") val topping: List<ToppingDto>?
)

data class BattersDto(
    @SerializedName("batter") val batter: List<BatterDto>?
)

data class BatterDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?
)

data class ToppingDto(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?
)
