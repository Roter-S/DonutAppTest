package com.example.donutapptest.ui.views.onboarding.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class OnboardingPage(
    @param:StringRes val title: Int,
    @param:StringRes val description: Int,
    @param:RawRes val lottieAnimation: Int
)
