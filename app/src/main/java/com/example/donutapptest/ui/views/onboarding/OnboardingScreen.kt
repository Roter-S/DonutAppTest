package com.example.donutapptest.ui.views.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.donutapptest.data.model.OnboardingPage
import com.example.donutapptest.data.source.local.OnboardingDataSource
import com.example.donutapptest.ui.components.LottieAnimationComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    val pages = OnboardingDataSource.getOnboardingPages()
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPage(
                page = pages[page],
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            repeat(pages.size) { index ->
                val color = if (pagerState.currentPage == index) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray
                }
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .height(10.dp)
                        .width(25.dp)
                        .background(color, CircleShape)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text("Anterior")
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            Button(
                onClick = {
                    if (pagerState.currentPage == pages.size - 1) {
                        onFinished()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    if (pagerState.currentPage == pages.size - 1) "Empezar" else "Siguiente"
                )
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimationComponent(
            animationRes = page.lottieAnimation,
            modifier = Modifier.padding(bottom = 32.dp),
            size = 200.dp,
        )

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 24.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(onFinished = {})
}

