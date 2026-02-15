package com.example.listify.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

// ---------------------------------------------------------
// ScreenType ENUM
// Represents device size classification.
// Used to apply responsive layouts in the app.
// ---------------------------------------------------------
enum class ScreenType {

    // Devices with smaller width (phones)
    PHONE,

    // Devices with larger width (tablets)
    TABLET
}

// ---------------------------------------------------------
// getScreenType()
// Detects the current device screen width at runtime
// and returns PHONE or TABLET accordingly.
// ---------------------------------------------------------
@Composable
fun getScreenType(): ScreenType {

    // LocalConfiguration gives access to device configuration
    // such as screen width, height, orientation, etc.
    val cfg = LocalConfiguration.current

    // If screen width is less than 600dp → treat as PHONE
    // 600dp is commonly used breakpoint for tablet detection
    return if (cfg.screenWidthDp < 600)
        ScreenType.PHONE
    else
        ScreenType.TABLET
}
