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

// Material 3 Expressive - Light Color Scheme
private val ExpressiveLightColorScheme = lightColorScheme(
    primary = ExpressivePrimary,
    onPrimary = ExpressiveOnPrimary,
    primaryContainer = ExpressivePrimaryContainer,
    onPrimaryContainer = ExpressiveOnPrimaryContainer,

    secondary = ExpressiveSecondary,
    onSecondary = ExpressiveOnSecondary,
    secondaryContainer = ExpressiveSecondaryContainer,
    onSecondaryContainer = ExpressiveOnSecondaryContainer,

    tertiary = ExpressiveTertiary,
    onTertiary = ExpressiveOnTertiary,
    tertiaryContainer = ExpressiveTertiaryContainer,
    onTertiaryContainer = ExpressiveOnTertiaryContainer,

    surface = ExpressiveSurface,
    onSurface = ExpressiveOnSurface,
    surfaceVariant = ExpressiveSurfaceVariant,
    onSurfaceVariant = ExpressiveOnSurfaceVariant,
    outline = ExpressiveOutline,
    outlineVariant = ExpressiveOutlineVariant,

    background = ExpressiveBackground,
    onBackground = ExpressiveOnBackground,

    error = ExpressiveError,
    onError = ExpressiveOnError,
    errorContainer = ExpressiveErrorContainer,
    onErrorContainer = ExpressiveOnErrorContainer,

    inversePrimary = ExpressiveInversePrimary,
    inverseSurface = ExpressiveInverseSurface,
    inverseOnSurface = ExpressiveInverseOnSurface
)

// Material 3 Expressive - Dark Color Scheme
private val ExpressiveDarkColorScheme = darkColorScheme(
    primary = ExpressiveDarkPrimary,
    onPrimary = ExpressiveDarkOnPrimary,
    primaryContainer = ExpressiveDarkPrimaryContainer,
    onPrimaryContainer = ExpressiveDarkOnPrimaryContainer,

    secondary = ExpressiveDarkSecondary,
    onSecondary = ExpressiveDarkOnSecondary,
    secondaryContainer = ExpressiveDarkSecondaryContainer,
    onSecondaryContainer = ExpressiveDarkOnSecondaryContainer,

    tertiary = ExpressiveDarkTertiary,
    onTertiary = ExpressiveDarkOnTertiary,
    tertiaryContainer = ExpressiveDarkTertiaryContainer,
    onTertiaryContainer = ExpressiveDarkOnTertiaryContainer,

    surface = ExpressiveDarkSurface,
    onSurface = ExpressiveDarkOnSurface,
    surfaceVariant = ExpressiveDarkSurfaceVariant,
    onSurfaceVariant = ExpressiveDarkOnSurfaceVariant,
    outline = ExpressiveDarkOutline,
    outlineVariant = ExpressiveDarkOutlineVariant,

    background = ExpressiveDarkBackground,
    onBackground = ExpressiveDarkOnBackground,

    error = ExpressiveDarkError,
    onError = ExpressiveDarkOnError,
    errorContainer = ExpressiveDarkErrorContainer,
    onErrorContainer = ExpressiveDarkOnErrorContainer,

    inversePrimary = ExpressiveInversePrimary,
    inverseSurface = ExpressiveInverseSurface,
    inverseOnSurface = ExpressiveInverseOnSurface
)

@Composable
fun DonutAppTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Mantenemos false para usar nuestra paleta expresiva personalizada
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> ExpressiveDarkColorScheme
        else -> ExpressiveLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}