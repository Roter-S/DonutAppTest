package com.example.donutapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.donutapptest.ui.theme.DonutAppTestTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DonutAppTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    verticalArrangement = Arrangement.Center
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("home/{username}") { backStackEntry ->
                            HomeScreen(backStackEntry.arguments?.getString("username"))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    val image: ImageBitmap = ImageBitmap.imageResource(R.drawable.logo_byte)
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var alertMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            title = "Alert",
            message = alertMessage,
            onDismissRequest = { showDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp, 50.dp, 30.dp, 0.dp)
    ) {
        Icon(
            bitmap = image,
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally),
            tint = MaterialTheme.colorScheme.primary
        )
        TextFieldUsername(
            value = username,
            onValueChange = { username = it },
            username = username
        )
        TextFieldPassword(
            value = password,
            onValueChange = { password = it },
            password = password,
            passwordHidden = passwordHidden,
            onPasswordHiddenChange = { passwordHidden = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                when {
                    username.isEmpty() && password.isEmpty() -> {
                        alertMessage = "Please enter a username and password"
                        showDialog = true
                    }
                    username.length < 8 -> {
                        alertMessage = "Username must be at least 8 characters"
                        showDialog = true
                    }
                    password.length < 6 || !password.any { it.isUpperCase() } -> {
                        alertMessage = "Password must be at least 6 characters and contain an uppercase letter"
                        showDialog = true
                    }
                    else -> {
                        navController.navigate("home/$username")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun AlertDialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}

@Composable
fun TextFieldUsername(
    value: String,
    onValueChange: (String) -> Unit,
    username: String,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(id = R.string.login_username),
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

@Composable
fun TextFieldPassword(
    value: String,
    onValueChange: (String) -> Unit,
    password: String,
    passwordHidden: Boolean,
    onPasswordHiddenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(id = R.string.login_password),
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

@Composable
fun HomeScreen(username: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome, $username!")
    }
}
