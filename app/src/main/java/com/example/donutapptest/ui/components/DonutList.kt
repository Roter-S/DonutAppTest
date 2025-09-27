package com.example.donutapptest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.donutapptest.data.model.Donut
import com.example.donutapptest.ui.preview.SampleData
import com.example.donutapptest.ui.views.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun DonutList(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    DonutListContent(
        donuts = viewModel.donuts,
        isLoading = viewModel.isLoading,
        modifier = modifier
    )
}

@Composable
fun DonutListContent(
    donuts: List<Donut>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var showScrollToTop by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleIndex ->
                showScrollToTop = firstVisibleIndex > 2
            }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (donuts.isEmpty() && isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(donuts.size) { index ->
                    DonutItem(donut = donuts[index])
                }
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
        if (showScrollToTop) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }, modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("↑")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonutListPreview() {
    DonutListContent(
        donuts = SampleData.createSampleDonuts(),
        isLoading = false
    )
}