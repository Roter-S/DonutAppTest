package com.example.donutapptest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.donutapptest.R

@Composable
fun LogoImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(id = R.string.app_name),
        modifier = modifier.fillMaxWidth()
    )
} 