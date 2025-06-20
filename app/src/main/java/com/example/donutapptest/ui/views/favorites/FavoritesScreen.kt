package com.example.donutapptest.ui.views.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.components.PlaceholderScreen
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(text = "Pantalla de Favoritos", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    DonutAppTestTheme {
        FavoritesScreen()
    }
} 