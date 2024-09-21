package com.example.donutapptest.ui.screens.login

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.donutapptest.R

@Composable
fun LoginScreen(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("home")
        }
    ) {
        Text(stringResource(id = R.string.login_btn))
    }
}