package com.example.donutapptest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.donutapptest.ui.components.LoaderScreen
import com.example.donutapptest.ui.views.login.LoginRoute
import com.example.donutapptest.ui.views.main.MainRoute
import com.example.donutapptest.ui.views.main.MainViewModel
import com.example.donutapptest.ui.views.register.RegisterRoute
import com.example.donutapptest.utils.enums.Screens

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var initialRoute: String? by remember { mutableStateOf(null) }
    val isUserLoggedIn by mainViewModel.isUserLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn != null && initialRoute == null) {
            initialRoute = if (isUserLoggedIn == true) Screens.HOME.route else Screens.LOGIN.route
        }
    }

    if (initialRoute == null) {
        LoaderScreen(modifier = modifier)
        return
    }

    NavHost(
        navController = navController,
        startDestination = initialRoute ?: Screens.LOGIN.route,
        modifier = modifier
    ) {
        composable(Screens.LOGIN.route) {
            LoginRoute(onNavigateToHome = {
                navController.navigate(Screens.HOME.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }, onNavigateToRegister = {
                navController.navigate(Screens.REGISTER.route)
            })
        }

        composable(Screens.REGISTER.route) {
            RegisterRoute(onNavigateToHome = {
                navController.navigate(Screens.HOME.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }, onNavigateToLogin = {
                navController.popBackStack()
            })
        }

        composable(Screens.HOME.route) {
            MainRoute(
                onLogoutSuccess = {
                    navController.navigate(Screens.LOGIN.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                })
        }
    }
}