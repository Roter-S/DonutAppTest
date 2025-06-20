package com.example.donutapptest.ui.views.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.components.PlaceholderScreen
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun CartScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(text = "Pantalla del Carrito", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    DonutAppTestTheme {
        CartScreen()
    }
} 