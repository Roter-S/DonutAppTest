package com.example.donutapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import com.example.donutapptest.ui.theme.auth.LoginScreen
import com.example.donutapptest.ui.theme.auth.RegisterScreen
import com.example.donutapptest.ui.theme.views.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DonutAppTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("home/{username}") { backStackEntry ->
                            HomeScreen(backStackEntry.arguments?.getString("username"))
                        }
                    }
                }
            }
        }
    }
}
