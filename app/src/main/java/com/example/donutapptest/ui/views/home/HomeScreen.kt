package com.example.donutapptest.ui.views.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun HomeScreen() {
    Text(text = "Welcome to Home Screen!")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    DonutAppTestTheme {
        HomeScreen()
    }
}