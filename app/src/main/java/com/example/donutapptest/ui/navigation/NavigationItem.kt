package com.example.donutapptest.ui.navigation

import com.example.donutapptest.data.model.Screen

sealed class NavigationItem(val route: String) {
    data object Login : NavigationItem(Screen.LOGIN.name)
    data object Home : NavigationItem(Screen.HOME.name)
}