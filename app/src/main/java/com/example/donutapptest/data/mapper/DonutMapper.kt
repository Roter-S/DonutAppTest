package com.example.donutapptest.data.mapper

import com.example.donutapptest.data.remote.dto.BatterDto
import com.example.donutapptest.data.remote.dto.DonutDto
import com.example.donutapptest.data.remote.dto.ToppingDto
import com.example.donutapptest.domain.model.Batter
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.model.Topping

fun DonutDto.toDomain(): Donut {
    return Donut(
        id = id.orEmpty(),
        type = type.orEmpty(),
        name = name.orEmpty(),
        ppu = ppu ?: 0.0,
        imageUrl = imageUrl.orEmpty(),
        batters = batters?.batter?.map { it.toDomain() }.orEmpty(),
        toppings = topping?.map { it.toDomain() }.orEmpty()
    )
}

fun BatterDto.toDomain(): Batter {
    return Batter(
        id = id.orEmpty(),
        type = type.orEmpty()
    )
}

fun ToppingDto.toDomain(): Topping {
    return Topping(
        id = id.orEmpty(),
        type = type.orEmpty()
    )
}
