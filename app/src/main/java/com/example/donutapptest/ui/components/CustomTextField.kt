package com.example.donutapptest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    username: String,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onSurface
    )
    val isError = username.isNotEmpty() && username.length < 8
    val textColor =
        if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Transparent, RoundedCornerShape(25.dp))
            .border(1.dp, textColor, RoundedCornerShape(25.dp)),
        singleLine = true,
        textStyle = TextStyle(
            color = if (isError) MaterialTheme.colorScheme.error else textColor,
        ),
        cursorBrush = SolidColor(textColor),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(25.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                innerTextField()
            }
        }
    )

    Spacer(modifier = Modifier.height(8.dp))
}