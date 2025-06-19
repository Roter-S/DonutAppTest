package com.example.donutapptest.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    // Colores principales usando los acentos del logo
    primary = DarkAccentPrimary,
    secondary = DarkAccentSecondary,
    tertiary = DarkAccentTertiary,
    
    background = DarkBackgroundColor,
    surface = DarkSurfaceColor,
    surfaceVariant = DarkSurfaceVariantColor,
    outline = DarkOutlineColor,
    
    // Colores de texto optimizados para dark theme
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFFFFFFFF),
    onBackground = DarkOnBackgroundColor,
    onSurface = DarkOnSurfaceColor,
    onSurfaceVariant = DarkOnSurfaceVariantColor
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
    dynamicColor: Boolean = false, // Cambiado a false para usar nuestra paleta personalizada
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
        colorScheme = colorScheme, typography = Typography, content = content
    )
}