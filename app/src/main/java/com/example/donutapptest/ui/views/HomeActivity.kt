package com.example.donutapptest.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.donutapptest.R
import com.example.donutapptest.data.Donut
import com.example.donutapptest.data.SessionManager
import com.example.donutapptest.viewmodel.DonutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername()
    val appName = stringResource(id = R.string.app_name)
    val donutViewModel: DonutViewModel = viewModel()
    val donuts = donutViewModel.donuts

    LaunchedEffect(Unit) {
        donutViewModel.fetchDonuts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = appName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    val expanded = remember { mutableStateOf(false) }
                    val logout = stringResource(id = R.string.logout)
                    Box {
                        IconButton(onClick = { expanded.value = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }) {
                            DropdownMenuItem(
                                text = { Text("$username") },
                                onClick = { /* Handle edit! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.SupervisedUserCircle,
                                        contentDescription = null
                                    )
                                }
                            )
                            HorizontalDivider()
                            DropdownMenuItem(
                                text = { Text(text = logout) },
                                onClick = {
                                    expanded.value = false
                                    navController.navigate("login") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                    sessionManager.clearSession()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Email,
                                        contentDescription = null
                                    )
                                },
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                donuts.value?.let { list ->
                    items(list) { donut ->
                        DonutItem(donut = donut)
                    }
                } ?: run {
                    item {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DonutItem(donut: Donut) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = donut.name, style = MaterialTheme.typography.titleLarge)
        Text(text = "Type: ${donut.type}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "PPU: ${donut.ppu}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Batters:", style = MaterialTheme.typography.bodyLarge)
        donut.batters.batter.forEach { batter ->
            Text(text = "- ${batter.type}", style = MaterialTheme.typography.bodyLarge)
        }
        Text(text = "Toppings:", style = MaterialTheme.typography.bodyLarge)
        donut.topping.forEach { topping ->
            Text(text = "- ${topping.type}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
