package com.example.donutapptest.ui.common

import com.example.donutapptest.core.common.UiText
import com.example.donutapptest.utils.enums.Alerts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

data class AppNotification(
    val message: UiText,
    val type: Alerts = Alerts.SUCCESS
)

@Singleton
class AppNotificationManager @Inject constructor() {
    private val _notifications = Channel<AppNotification>(Channel.BUFFERED)
    val notifications = _notifications.receiveAsFlow()

    suspend fun showNotification(message: UiText, type: Alerts = Alerts.SUCCESS) {
        _notifications.send(AppNotification(message, type))
    }
}
