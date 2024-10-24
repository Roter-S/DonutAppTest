package com.example.donutapptest.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldNotification(
    scope: CoroutineScope,
    message: String,
    onDismiss: () -> Unit,
    type: String = "success" // default type is success
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = message) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                onDismiss()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = getColorForType(type),
                    actionColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}

@Composable
fun getColorForType(type: String): Color = when (type) {
    "error" -> Color.Red
    "warning" -> Color.Yellow
    "success" -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.colorScheme.surface
}
