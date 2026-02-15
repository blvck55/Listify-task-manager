package com.example.listify.ui.theme

// ---------------------------------------------------------
// ThemeMode ENUM
// Represents the three possible theme options in the app
// Used in ProfileScreen and ListifyTheme to switch themes
// ---------------------------------------------------------
enum class ThemeMode {

    // Follow the device/system theme setting
    // If system is in dark mode → app uses dark mode
    // If system is in light mode → app uses light mode
    SYSTEM,

    // Force light theme regardless of system setting
    LIGHT,

    // Force dark theme regardless of system setting
    DARK
}
