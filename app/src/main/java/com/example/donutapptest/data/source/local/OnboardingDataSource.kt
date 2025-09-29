package com.example.donutapptest.data.source.local

import com.example.donutapptest.data.model.OnboardingPage
import com.example.donutapptest.R

object OnboardingDataSource {
    fun getOnboardingPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                title = "Bienvenido a DonutApp",
                description = "Explora nuestras donas frescas y deliciosas.",
                lottieAnimation = R.raw.yummy_donut
            ),
            OnboardingPage(
                title = "Descubre & Likes",
                description = "Marca tus favoritas con ❤️.",
                lottieAnimation = R.raw.add_to_favorites
            ),
            OnboardingPage(
                title = "Carrito & Pedido",
                description = "Agrega al carrito y disfruta con un clic.",
                lottieAnimation = R.raw.empty_cart
            )
        )
    }
}
