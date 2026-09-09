package com.example.donutapptest.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.donutapptest.ui.common.AppNotification
import com.example.donutapptest.ui.common.AppNotificationManager

@Composable
fun ScaffoldNotificationObserver(
    notificationManager: AppNotificationManager
) {
    val context = LocalContext.current
    var currentNotification by remember { mutableStateOf<AppNotification?>(null) }

    LaunchedEffect(notificationManager) {
        notificationManager.notifications.collect { notification ->
            currentNotification = notification
        }
    }

    currentNotification?.let { notif ->
        ScaffoldNotification(
            message = notif.message.asString(context),
            type = notif.type,
            onDismiss = { currentNotification = null }
        )
    }
}