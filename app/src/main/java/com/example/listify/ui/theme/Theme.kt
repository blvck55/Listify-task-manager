package com.example.listify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

// ---------------------------------------------------------
// LIGHT COLOR SCHEME
// Defines the color palette used when app is in Light Mode
// ---------------------------------------------------------
private val LightColors = lightColorScheme(

    // Primary brand color (used for buttons, highlights, etc.)
    primary = PrimaryBlue,

    // Text/icon color displayed on top of primary color
    onPrimary = androidx.compose.ui.graphics.Color.White,

    // Secondary accent color
    secondary = PrimaryBlueLight,

    // Main background color of the app
    background = LightBackground,

    // Surface color (cards, sheets, containers)
    surface = LightSurface,

    // Default text color on surfaces
    onSurface = androidx.compose.ui.graphics.Color.Black
)

// ---------------------------------------------------------
// DARK COLOR SCHEME
// Defines the color palette used when app is in Dark Mode
// ---------------------------------------------------------
private val DarkColors = darkColorScheme(

    // Primary brand color in dark theme
    primary = PrimaryBlueLight,

    // Text/icon color displayed on primary color
    onPrimary = androidx.compose.ui.graphics.Color.White,

    // Main background color in dark mode
    background = DarkBackground,

    // Surface color for cards and containers in dark mode
    surface = DarkSurface,

    // Default text color on dark surfaces
    onSurface = androidx.compose.ui.graphics.Color.White
)

// ---------------------------------------------------------
// MAIN THEME COMPOSABLE
// Wraps the entire app and applies selected color scheme
// ---------------------------------------------------------
@Composable
fun ListifyTheme(

    // Determines whether dark theme should be applied.
    // By default, follows the system setting.
    darkTheme: Boolean = isSystemInDarkTheme(),

    // UI content that will use this theme
    content: @Composable () -> Unit
) {

    // Choose correct color scheme based on theme mode
    val colors = if (darkTheme) DarkColors else LightColors

    // Apply Material 3 theme configuration
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,   // Uses custom Typography.kt
        content = content          // Applies theme to all child composables
    )
}
