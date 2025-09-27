package com.example.donutapptest.ui.views.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.donutapptest.ui.components.DonutList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    BackHandler {
        (context as? Activity)?.finishAffinity()
    }
    DonutList(modifier = modifier, viewModel = homeViewModel)
}