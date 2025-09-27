package com.example.donutapptest.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.donutapptest.main.MainViewModel
import com.example.donutapptest.ui.views.login.LoginScreen
import com.example.donutapptest.ui.views.main.MainScreen
import com.example.donutapptest.ui.views.register.RegisterScreen
import com.example.donutapptest.utils.enums.Screens

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var initialRoute: String? by remember { mutableStateOf(null) }
    val isUserLoggedIn by mainViewModel.isUserLoggedInFlow.collectAsState()

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn != null && initialRoute == null) {
            initialRoute = if (isUserLoggedIn == true) Screens.HOME.route else Screens.LOGIN.route
        }
    }

    if (initialRoute == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(navController = navController, startDestination = initialRoute ?: Screens.LOGIN.route) {
        composable(Screens.LOGIN.route) { LoginScreen(navController = navController) }
        composable(Screens.REGISTER.route) { RegisterScreen(navController = navController) }
        composable(Screens.HOME.route) { MainScreen(navController = navController) }
    }
}