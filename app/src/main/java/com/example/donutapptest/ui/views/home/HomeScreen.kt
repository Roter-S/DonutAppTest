package com.example.donutapptest.ui.views.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    BackHandler {
        (context as? Activity)?.finishAffinity()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Welcome to Home Screen!")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DonutAppTestTheme {
        HomeScreen()
    }
}