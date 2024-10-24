package com.example.donutapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.navigation.NavigationComponent
import com.example.donutapptest.ui.theme.DonutAppTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DonutAppTestTheme(dynamicColor = false) {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) { innerPadding ->
                    NavigationComponent(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
