package com.example.donutapptest.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.donutapptest.utils.NotificationManager

@Composable
fun ScaffoldNotificationObserver() {
    val notificationState = NotificationManager.notificationState.collectAsState().value
    val scope = rememberCoroutineScope()

    notificationState?.let { state ->
        ScaffoldNotification(
            scope = scope,
            message = state.message,
            onDismiss = { NotificationManager.clearNotification() },
            type = state.type
        )
    }
}