package com.example.donutapptest.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    @param:StringRes val title: Int,
    val icon: ImageVector
)