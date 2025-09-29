package com.example.donutapptest.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "donut_app_prefs",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) = prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, value) }
}

