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
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.common.AppNotificationManager
import com.example.donutapptest.ui.components.LoaderScreen
import com.example.donutapptest.ui.components.ScaffoldNotificationObserver
import com.example.donutapptest.ui.navigation.NavigationComponent
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import com.example.donutapptest.ui.views.main.MainViewModel
import com.example.donutapptest.ui.views.onboarding.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var notificationManager: AppNotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DonutAppTestTheme(
                dynamicColor = false
            ) {
                val mainViewModel: MainViewModel = hiltViewModel()
                val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsStateWithLifecycle()
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    when (isOnboardingCompleted) {
                        null -> {
                            LoaderScreen(modifier = Modifier.padding(innerPadding))
                        }
                        false -> {
                            OnboardingScreen(
                                onFinished = {
                                    mainViewModel.completeOnboarding()
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        true -> {
                            ScaffoldNotificationObserver(notificationManager = notificationManager)
                            NavigationComponent(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding),
                                mainViewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
