package com.example.donutapptest.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

@Composable
fun CustomIconImage(
    image: ImageBitmap,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Icon(
        bitmap = image,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = color
    )
}