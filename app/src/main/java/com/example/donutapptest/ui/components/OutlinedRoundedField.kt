package com.example.donutapptest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedRoundedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    errorMessage: String? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val isError = errorMessage != null
    val baseColor =
        if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.3f
        )
    val errorColor = MaterialTheme.colorScheme.error

    val borderColor = if (isError) errorColor else baseColor
    val textColor = if (isError) errorColor else baseColor
    val iconTint = if (isError) errorColor else baseColor

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label, color = textColor, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp, color = borderColor, shape = RoundedCornerShape(24.dp)
                )
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                    cursorBrush = SolidColor(textColor),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = if (keyboardType == KeyboardType.Password && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 18.dp)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = textColor.copy(alpha = 0.6f),
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                            innerTextField()
                        }
                    },
                    enabled = enabled
                )
                if (keyboardType == KeyboardType.Password) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = iconTint,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(enabled = enabled) { passwordVisible = !passwordVisible })
                }
            }
        }
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp),
            )
        }
    }
}
