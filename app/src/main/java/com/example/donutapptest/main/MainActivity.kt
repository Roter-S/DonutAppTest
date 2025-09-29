package com.example.donutapptest.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.components.ScaffoldNotificationObserver
import com.example.donutapptest.ui.navigation.NavigationComponent
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import com.example.donutapptest.ui.views.onboarding.OnboardingScreen
import com.example.donutapptest.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        preferencesHelper = PreferencesHelper(this)

        setContent {
            DonutAppTestTheme(
                dynamicColor = true
            ) {
                var showOnboarding by remember { mutableStateOf(!preferencesHelper.isOnboardingCompleted) }
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    if (showOnboarding) {
                        OnboardingScreen(
                            onFinished = {
                                preferencesHelper.isOnboardingCompleted = true
                                showOnboarding = false
                            }
                        )
                    } else {
                        ScaffoldNotificationObserver()
                        NavigationComponent(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
