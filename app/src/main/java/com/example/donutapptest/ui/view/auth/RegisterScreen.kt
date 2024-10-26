package com.example.donutapptest.ui.view.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.viewmodel.auth.RegisterViewModel

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