package com.example.donutapptest.ui.views.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController? = null,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onLogout: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val userState = homeViewModel.user.collectAsState()
    val isLoggedIn = homeViewModel.isLoggedIn.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    BackHandler {
        (context as? Activity)?.finishAffinity()
    }
    LaunchedEffect(isLoggedIn.value) {
        if (isLoggedIn.value == false) {
            navController?.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .height(220.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = userState.value?.username ?: "Usuario",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextButton(
                    onClick = {
                        homeViewModel.logout {
                            onLogout?.invoke()
                        }
                    }, modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text("Cerrar sesión")
                }
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Home") }, navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                })
            }) { innerPadding ->
            Text(
                text = "Welcome to Home Screen!",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    DonutAppTestTheme {
        HomeScreen()
    }
}