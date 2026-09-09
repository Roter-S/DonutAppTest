package com.example.donutapptest.ui.views.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.donutapptest.R
import com.example.donutapptest.ui.views.main.model.BottomNavItem
import com.example.donutapptest.utils.enums.Screens

object BottomBarDataSource {
    fun getBottomBarItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                route = Screens.HOME.route,
                title = R.string.bottom_nav_home,
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                route = Screens.FAVORITES.route,
                title = R.string.bottom_nav_favorites,
                icon = Icons.Default.Favorite
            ),
            BottomNavItem(
                route = Screens.CART.route,
                title = R.string.bottom_nav_cart,
                icon = Icons.Default.ShoppingCart
            )
        )
    }
}
