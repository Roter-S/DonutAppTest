package com.example.donutapptest.ui.theme.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.donutapptest.R
import com.example.donutapptest.ui.theme.components.AlertDialog
import com.example.donutapptest.ui.theme.components.CustomIconImage
import com.example.donutapptest.ui.theme.components.CustomTextField
import com.example.donutapptest.ui.theme.components.CustomTextFieldPassword

@Composable
fun RegisterScreen(navController: NavHostController) {
    val image: ImageBitmap = ImageBitmap.imageResource(R.drawable.logo_byte)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var confirmPasswordHidden by rememberSaveable { mutableStateOf(true) }
    var alertMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val enterUserAndPass = stringResource(id = R.string.login_error_enter_username_and_password)
    val usernameLength = stringResource(id = R.string.login_error_username_length)
    val passwordLength = stringResource(id = R.string.login_error_password_length)
    val passwordNotMatch = stringResource(id = R.string.register_error_password_not_match)

    if (showDialog) {
        AlertDialog(
            title = "",
            message = alertMessage,
            onDismissRequest = { showDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 30.dp, 30.dp, 0.dp)
    ) {
        CustomIconImage(
            image = image,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )
        CustomTextField(
            label = stringResource(id = R.string.login_username),
            value = username,
            onValueChange = { username = it },
            username = username
        )
        CustomTextFieldPassword(
            label = stringResource(id = R.string.login_password),
            value = password,
            onValueChange = { password = it },
            password = password,
            passwordHidden = passwordHidden,
            onPasswordHiddenChange = { passwordHidden = it }
        )
        CustomTextFieldPassword(
            label = stringResource(id = R.string.register_confirm_password),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            password = confirmPassword,
            passwordHidden = confirmPasswordHidden,
            onPasswordHiddenChange = { confirmPasswordHidden = it }
        )
        Button(
            onClick = {
                when {
                    username.isEmpty() && password.isEmpty() -> {
                        alertMessage = enterUserAndPass
                        showDialog = true
                    }

                    username.length < 8 -> {
                        alertMessage = usernameLength
                        showDialog = true
                    }

                    password.length < 6 || !password.any { it.isUpperCase() } -> {
                        alertMessage =
                            passwordLength
                        showDialog = true
                    }

                    password != confirmPassword -> {
                        alertMessage = passwordNotMatch
                        showDialog = true
                    }

                    else -> {
                        navController.navigate("login")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.register_title))
        }
    }
}