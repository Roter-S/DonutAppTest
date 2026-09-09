package com.example.donutapptest.ui.views.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.donutapptest.domain.model.Donut
import com.example.donutapptest.ui.components.DonutList
import com.example.donutapptest.ui.preview.SampleData
import com.example.donutapptest.ui.theme.DonutAppTestTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    BackHandler {
        (context as? Activity)?.finishAffinity()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onFavoriteClick = viewModel::toggleFavorite,
        onRetry = viewModel::loadDonuts,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onFavoriteClick: (Donut, Boolean) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    DonutList(
        donuts = uiState.donuts,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onFavoriteClick = onFavoriteClick,
        onRetry = onRetry,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    DonutAppTestTheme {
        HomeScreen(
            uiState = HomeUiState(
                donuts = SampleData.createSampleDonuts(5),
                isLoading = false
            ),
            onFavoriteClick = { _, _ -> },
            onRetry = {}
        )
    }
}