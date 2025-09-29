package com.example.donutapptest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.donutapptest.R

@Composable
fun LoaderScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimationComponent(
            animationRes = R.raw.donut_bounce,
            modifier = Modifier,
            size = 150.dp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoaderScreenPreview() {
    LoaderScreen()
}