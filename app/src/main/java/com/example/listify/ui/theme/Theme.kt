package com.example.listify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = PrimaryBlueLight,
    background = LightBackground,
    surface = LightSurface,
    onSurface = androidx.compose.ui.graphics.Color.Black
)

private val DarkColors = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = androidx.compose.ui.graphics.Color.White
)

@Composable
fun ListifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
