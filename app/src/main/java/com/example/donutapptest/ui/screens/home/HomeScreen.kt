package com.example.donutapptest.ui.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.donutapptest.R

@Composable
fun HomeScreen(navController: NavHostController) {
    Text(stringResource(id = R.string.login_btn))
}