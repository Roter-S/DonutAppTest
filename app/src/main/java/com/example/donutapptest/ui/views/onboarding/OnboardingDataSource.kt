package com.example.donutapptest.ui.views.onboarding

import com.example.donutapptest.R
import com.example.donutapptest.ui.views.onboarding.model.OnboardingPage

object OnboardingDataSource {
    fun getOnboardingPages(): List<OnboardingPage> {
        return listOf(
            OnboardingPage(
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_desc_1,
                lottieAnimation = R.raw.yummy_donut
            ),
            OnboardingPage(
                title = R.string.onboarding_title_2,
                description = R.string.onboarding_desc_2,
                lottieAnimation = R.raw.add_to_favorites
            ),
            OnboardingPage(
                title = R.string.onboarding_title_3,
                description = R.string.onboarding_desc_3,
                lottieAnimation = R.raw.empty_cart
            )
        )
    }
}
