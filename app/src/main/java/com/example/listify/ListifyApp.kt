package com.example.listify

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import com.example.listify.navigation.NavGraph
import com.example.listify.ui.theme.ListifyTheme
import com.example.listify.ui.theme.ThemeMode

/**
 * The main entry point for the Compose UI of the application.
 * It manages the theme state (Light, Dark, or System) and sets up navigation.
 */
@Composable
fun ListifyApp() {
    // Controller for app-wide navigation
    val navController = rememberNavController()

    // Persistent state for the theme mode
    var themeMode by rememberSaveable { mutableStateOf(ThemeMode.SYSTEM) }

    // Determine if dark theme should be applied based on the selected mode
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    // Apply the application theme wrapper
    ListifyTheme(darkTheme = darkTheme) {
        // Set up the navigation graph
        NavGraph(
            navController = navController,
            themeMode = themeMode,
            onThemeModeChange = { themeMode = it }
        )
    }
}
