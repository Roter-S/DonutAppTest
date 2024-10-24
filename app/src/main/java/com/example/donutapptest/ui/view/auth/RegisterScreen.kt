package com.example.donutapptest.ui.view.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun RegisterScreen() {
    Text(text = "Welcome to Register Screen!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    DonutAppTestTheme {
        RegisterScreen()
    }
}