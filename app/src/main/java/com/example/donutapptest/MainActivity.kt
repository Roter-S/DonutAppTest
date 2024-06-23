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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.donutapptest.data.SessionManager
import com.example.donutapptest.data.UserDatabase
import com.example.donutapptest.data.UserRepository
import com.example.donutapptest.ui.views.auth.LoginScreen
import com.example.donutapptest.ui.views.HomeScreen
import com.example.donutapptest.ui.views.auth.RegisterScreen
import com.example.donutapptest.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = UserDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(database.userDao())
        val sessionManager = SessionManager(this)
        val username = sessionManager.getUsername()
        setContent {
            DonutAppTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center
                ) {
                    val navController = rememberNavController()
                    val startDestination = if (username != null) {
                        "home/${sessionManager.getUsername()}"
                    } else {
                        "login"
                    }
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("login") {
                            LoginScreen(navController, userRepository, sessionManager)
                        }
                        composable("register") {
                            val userViewModel = viewModel<UserViewModel>()
                            RegisterScreen(navController = navController, userViewModel = userViewModel)
                        }

                        composable("home/{username}") { backStackEntry ->
                            HomeScreen(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}