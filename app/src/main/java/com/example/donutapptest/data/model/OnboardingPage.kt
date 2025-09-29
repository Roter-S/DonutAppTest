package com.example.donutapptest.data.model

import androidx.annotation.RawRes

data class OnboardingPage(
    val title: String,
    val description: String,
    @field:RawRes val lottieAnimation: Int
)