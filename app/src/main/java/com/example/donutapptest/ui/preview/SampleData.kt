package com.example.donutapptest.ui.preview

import com.example.donutapptest.domain.model.Batter
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.domain.model.Topping

object SampleData {

    val sampleBatters: List<Batter> = listOf(
        Batter(id = "1001", type = "Regular"),
        Batter(id = "1002", type = "Chocolate"),
        Batter(id = "1003", type = "Blueberry"),
        Batter(id = "1004", type = "Devil's Food")
    )

    val sampleToppings: List<Topping> = listOf(
        Topping(id = "5001", type = "None"),
        Topping(id = "5002", type = "Glazed"),
        Topping(id = "5005", type = "Sugar"),
        Topping(id = "5007", type = "Powdered Sugar"),
        Topping(id = "5006", type = "Chocolate with Sprinkles"),
        Topping(id = "5003", type = "Chocolate"),
        Topping(id = "5004", type = "Maple")
    )

    val sampleDonut = Donut(
        id = "1",
        name = "Glazed Donut",
        type = "Glazed",
        ppu = 0.99,
        imageUrl = "https://images.unsplash.com/photo-1551024709-8f23befc6f87",
        batters = sampleBatters,
        toppings = sampleToppings,
        isFavorite = false
    )

    fun createSampleDonuts(count: Int = 20): List<Donut> {
        return List(count) { index ->
            sampleDonut.copy(
                id = (index + 1).toString(),
                name = "Donut ${index + 1}",
                isFavorite = index % 3 == 0
            )
        }
    }
}

