package com.example.donutapptest.ui.views.register

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel
) {
    Text(text = "Welcome to Register Screen!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    val registerViewModel = remember {
        RegisterViewModel()
    }
    RegisterScreen(navController = navController, registerViewModel = registerViewModel)
}