package com.example.listify.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------
// TYPOGRAPHY CONFIGURATION
// Defines custom text styles used across the app.
// Applied globally inside ListifyTheme via MaterialTheme.
// ---------------------------------------------------------

val Typography = Typography(

    // -----------------------------------------------------
    // bodyLarge
    // Used for normal paragraph text, descriptions,
    // and general content across screens.
    // -----------------------------------------------------
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,   // Default Android font (Roboto)
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp                    // Standard readable size
    ),

    // -----------------------------------------------------
    // titleLarge
    // Used for section headers and screen titles.
    // -----------------------------------------------------
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),

    // -----------------------------------------------------
    // headlineSmall
    // Used for prominent headings such as:
    // - Task titles
    // - Main screen headings
    // -----------------------------------------------------
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Black,
        fontSize = 26.sp
    )
)
