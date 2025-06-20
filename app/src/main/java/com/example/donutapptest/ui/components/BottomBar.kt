package com.example.donutapptest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.donutapptest.R
import com.example.donutapptest.utils.enums.Screens

@Composable
fun BottomBar(
    currentRoute: String, onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem(
            route = Screens.HOME.route,
            title = stringResource(R.string.bottom_nav_home),
            icon = Icons.Default.Home
        ), BottomNavItem(
            route = Screens.FAVORITES.route,
            title = stringResource(R.string.bottom_nav_favorites),
            icon = Icons.Default.Favorite
        ), BottomNavItem(
            route = Screens.CART.route,
            title = stringResource(R.string.bottom_nav_cart),
            icon = Icons.Default.ShoppingCart
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (currentRoute == item.route) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (currentRoute == item.route) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

private data class BottomNavItem(
    val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector
) 