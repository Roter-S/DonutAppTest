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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.donutapptest.R

@Composable
fun CustomTextFieldPassword(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    password: String,
    passwordHidden: Boolean,
    onPasswordHiddenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = label,
        color = MaterialTheme.colorScheme.onSurface
    )
    val isError =
        password.isNotEmpty() && (password.length < 6 || !password.any { it.isUpperCase() })
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
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(Color.Transparent, RoundedCornerShape(25.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                innerTextField()
                IconToggleButton(
                    checked = passwordHidden,
                    onCheckedChange = onPasswordHiddenChange,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    if (passwordHidden) {
                        Icon(
                            Icons.Default.Visibility,
                            contentDescription = "hide password",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            Icons.Default.VisibilityOff,
                            contentDescription = "show password",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            }
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}