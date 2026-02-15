package com.example.listify.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class ScreenType { PHONE, TABLET }

@Composable
fun getScreenType(): ScreenType {
    val cfg = LocalConfiguration.current
    return if (cfg.screenWidthDp < 600) ScreenType.PHONE else ScreenType.TABLET
}
