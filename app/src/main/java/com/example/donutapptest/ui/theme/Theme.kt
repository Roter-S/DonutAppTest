package com.example.donutapptest.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val BluePrimary = Color(0xFF2699FB)  // Primary Color
val DarkBlueSecondary = Color(0xFF0A2459)  // Secondary Color

val LightBlueTertiary = Color(0xFF82CFFF)  // Tertiary Color, un tono más claro de azul
val DarkerPrimary = Color(0xFF1A7DC0)  // Versión más oscura del primary
val LighterSecondary = Color(0xFF123972)  // Versión más clara del secondary
val BackgroundColor = Color(0xFFF5F9FF)  // Fondo, basado en un tono claro y neutro
val SurfaceColor = Color(0xFFFFFFFF)  // Superficies en blanco
val OnPrimaryColor = Color(0xFFFFFFFF)  // Texto sobre primary
val OnSecondaryColor = Color(0xFFFFFFFF)  // Texto sobre secondary
val OnTertiaryColor = Color(0xFF000000)  // Texto sobre terciary (negro para contraste)
val OnBackgroundColor = Color(0xFF0A2459)  // Texto sobre el fondo
val OnSurfaceColor = Color(0xFF0A2459)  // Texto sobre superficies

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = DarkBlueSecondary,
    tertiary = LightBlueTertiary,
    background = DarkBlueSecondary,
    surface = DarkerPrimary,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onTertiary = OnTertiaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = DarkBlueSecondary,
    tertiary = LightBlueTertiary,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onTertiary = OnTertiaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor
)

@Composable
fun DonutAppTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}