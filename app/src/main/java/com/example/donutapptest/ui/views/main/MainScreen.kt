package com.example.donutapptest.ui.views.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.donutapptest.R
import com.example.donutapptest.ui.components.BottomBar
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import com.example.donutapptest.ui.views.cart.CartScreen
import com.example.donutapptest.ui.views.favorites.FavoritesScreen
import com.example.donutapptest.ui.views.home.HomeRoute
import com.example.donutapptest.utils.enums.Screens

@Composable
fun MainRoute(
    onLogoutSuccess: () -> Unit,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val username by mainViewModel.username.collectAsStateWithLifecycle()
    val isLoggedOut by mainViewModel.isLoggedOut.collectAsStateWithLifecycle()

    var currentRoute by remember { mutableStateOf(Screens.HOME.route) }

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            mainViewModel.resetLogoutState()
            onLogoutSuccess()
        }
    }

    MainScreenContent(
        username = username ?: stringResource(id = R.string.app_name),
        currentRoute = currentRoute,
        onNavigate = { newRoute -> currentRoute = newRoute },
        onLogoutClick = mainViewModel::logout,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    username: String,
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val title = when (currentRoute) {
        Screens.HOME.route -> stringResource(R.string.bottom_nav_home)
        Screens.FAVORITES.route -> stringResource(R.string.bottom_nav_favorites)
        Screens.CART.route -> stringResource(R.string.bottom_nav_cart)
        else -> ""
    }

    Scaffold(
        modifier = modifier,
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
                                style = MaterialTheme.typography.labelLarge
                            )
                            HorizontalDivider()
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.nav_logout)) },
                                onClick = {
                                    menuExpanded = false
                                    onLogoutClick()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
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
        val contentModifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)

        when (currentRoute) {
            Screens.HOME.route -> HomeRoute(modifier = contentModifier)
            Screens.FAVORITES.route -> FavoritesScreen(modifier = contentModifier)
            Screens.CART.route -> CartScreen(modifier = contentModifier)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenContentPreview() {
    DonutAppTestTheme {
        MainScreenContent(
            username = "donutlover@example.com",
            currentRoute = Screens.HOME.route,
            onNavigate = {},
            onLogoutClick = {}
        )
    }
}