package com.example.listify.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


// RoleBadge Component
// Displays a small rounded label indicating user role.
// Used in:
// - DashboardScreen (shows ADMIN / USER / GUEST)
@Composable
fun RoleBadge(label: String) {

    // Surface used as a background container for the badge
    Surface(

        // Light tinted background using primary color
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),

        // Text color inside badge
        contentColor = MaterialTheme.colorScheme.primary,

        // Fully rounded shape (pill style)
        shape = RoundedCornerShape(999.dp)
    ) {

        // Badge text (role label)
        Text(
            text = label,
            fontWeight = FontWeight.Bold,

            // Internal padding for spacing inside badge
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
        )
    }
}
