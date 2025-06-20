package com.example.donutapptest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.donutapptest.ui.theme.ExpressiveAlertError
import com.example.donutapptest.ui.theme.ExpressiveAlertInfo
import com.example.donutapptest.ui.theme.ExpressiveAlertSuccess
import com.example.donutapptest.ui.theme.ExpressiveAlertWarning
import com.example.donutapptest.ui.theme.ExpressiveOnAlertError
import com.example.donutapptest.ui.theme.ExpressiveOnAlertInfo
import com.example.donutapptest.ui.theme.ExpressiveOnAlertSuccess
import com.example.donutapptest.ui.theme.ExpressiveOnAlertWarning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScaffoldNotification(
    scope: CoroutineScope, message: String, onDismiss: () -> Unit, type: String = "SUCCESS"
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = message) {
        scope.launch {
            snackBarHostState.showSnackbar(
                message = message, duration = SnackbarDuration.Short
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = {
                CustomSnackbarContent(
                    message = message, onDismiss = onDismiss, type = type
                )
            })
    }
}

@Composable
fun CustomSnackbarContent(
    message: String, onDismiss: () -> Unit, type: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(getColorForType(type), RoundedCornerShape(22.dp))
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = getColorOnType(type)
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = getColorOnType(type)
                )
            }
        }
    }
}

@Composable
fun getColorForType(type: String): Color = when (type) {
    "INFO" -> ExpressiveAlertInfo
    "SUCCESS" -> ExpressiveAlertSuccess
    "WARNING" -> ExpressiveAlertWarning
    "ERROR" -> ExpressiveAlertError
    else -> MaterialTheme.colorScheme.surface
}

@Composable
fun getColorOnType(type: String): Color = when (type) {
    "INFO" -> ExpressiveOnAlertInfo
    "SUCCESS" -> ExpressiveOnAlertSuccess
    "WARNING" -> ExpressiveOnAlertWarning
    "ERROR" -> ExpressiveOnAlertError
    else -> MaterialTheme.colorScheme.onSurface
}
