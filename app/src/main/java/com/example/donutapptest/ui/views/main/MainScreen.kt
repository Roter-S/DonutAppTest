package com.example.donutapptest.ui.views.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.donutapptest.R
import com.example.donutapptest.ui.components.BottomBar
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import com.example.donutapptest.ui.views.cart.CartScreen
import com.example.donutapptest.ui.views.favorites.FavoritesScreen
import com.example.donutapptest.ui.views.home.HomeScreen
import com.example.donutapptest.utils.enums.BottomNavRoutes
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    onLogout: (() -> Unit)? = null
) {
    var currentRoute by remember { mutableStateOf(BottomNavRoutes.HOME.route) }
    val username by mainScreenViewModel.username.collectAsState()
    val logoutHandler: () -> Unit = {
        onLogout?.let { mainScreenViewModel.logout(it) }
    }

    MainScreenContent(
        username = username ?: "Usuario",
        currentRoute = currentRoute,
        onNavigate = { newRoute -> currentRoute = newRoute },
        onLogout = logoutHandler
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    username: String,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val title = when (currentRoute) {
        BottomNavRoutes.HOME.route -> stringResource(R.string.bottom_nav_home)
        BottomNavRoutes.FAVORITES.route -> stringResource(R.string.bottom_nav_favorites)
        BottomNavRoutes.CART.route -> stringResource(R.string.bottom_nav_cart)
        else -> ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(id = R.string.more_options)
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            offset = DpOffset(x = (-8).dp, y = 0.dp)
                        ) {
                            Text(
                                text = username,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.labelLarge,
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.nav_logout)) },
                                onClick = {
                                    onLogout()
                                    menuExpanded = false
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Logout,
                                        contentDescription = stringResource(R.string.nav_logout)
                                    )
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
        when (currentRoute) {
            BottomNavRoutes.HOME.route -> HomeScreen(modifier = modifier)
            BottomNavRoutes.FAVORITES.route -> FavoritesScreen(modifier = modifier)
            BottomNavRoutes.CART.route -> CartScreen(modifier = modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DonutAppTestTheme {
        MainScreenContent(
            username = "Preview User",
            currentRoute = BottomNavRoutes.HOME.route,
            onNavigate = {},
            onLogout = {}
        )
    }
} 