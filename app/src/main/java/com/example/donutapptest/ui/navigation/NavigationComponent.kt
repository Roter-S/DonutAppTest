package com.example.donutapptest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.donutapptest.main.MainViewModel
import com.example.donutapptest.ui.views.home.HomeScreen
import com.example.donutapptest.ui.views.login.LoginScreen
import com.example.donutapptest.ui.views.login.LoginViewModel
import com.example.donutapptest.ui.views.register.RegisterScreen
import com.example.donutapptest.ui.views.register.RegisterViewModel
import com.example.donutapptest.utils.enums.Screens

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        if (mainViewModel.isUserLoggedIn()) {
            navController.navigate(Screens.HOME.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        } else {
            navController.navigate(Screens.LOGIN.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
    NavHost(navController = navController, startDestination = Screens.LOGIN.route) {
        composable(Screens.LOGIN.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(Screens.REGISTER.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(navController = navController, registerViewModel = registerViewModel)
        }
        composable(Screens.HOME.route) {
            HomeScreen()
        }
    }
}