package com.example.donutapptest.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.donutapptest.R
import com.example.donutapptest.data.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername()
    val appName = stringResource(id = R.string.app_name)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = appName, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                actions = {
                    val expanded = remember { mutableStateOf(false) }
                    val logout = stringResource(id = R.string.logout)
                    Box() {
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
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}
