package com.example.donutapptest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavigationPromptRow(
    navController: NavController, promptTextId: Int, actionTextId: Int, navigationRoute: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = promptTextId),
        )
        Text(
            text = stringResource(id = actionTextId),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable {
                    navController.navigate(navigationRoute)
                })
    }
}
