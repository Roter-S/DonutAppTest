package com.example.donutapptest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.donutapptest.ui.view.HomeScreen
import com.example.donutapptest.ui.view.auth.LoginScreen
import com.example.donutapptest.ui.view.auth.RegisterScreen
import com.example.donutapptest.ui.viewmodel.auth.LoginViewModel
import com.example.donutapptest.ui.viewmodel.auth.RegisterViewModel
import com.example.donutapptest.util.enums.Screen

@Composable
fun NavigationComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.LOGIN.route) {
        composable(Screen.LOGIN.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(Screen.REGISTER.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }
        composable(Screen.HOME.route) {
            HomeScreen()
        }
    }
}