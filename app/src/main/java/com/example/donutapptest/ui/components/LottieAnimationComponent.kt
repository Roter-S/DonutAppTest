package com.example.donutapptest.ui.components

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationComponent(
    @RawRes animationRes: Int,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    iterations: Int = LottieConstants.IterateForever,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(size)
    )
}
