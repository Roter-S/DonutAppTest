package com.example.donutapptest.ui.views.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Pantalla del Carrito")
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    DonutAppTestTheme {
        CartScreen()
    }
} 