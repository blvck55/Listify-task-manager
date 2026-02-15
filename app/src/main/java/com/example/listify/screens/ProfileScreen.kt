package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.data.AuthManager
import com.example.listify.navigation.Routes
import com.example.listify.ui.theme.ThemeMode
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType

@Composable
fun ProfileScreen(
    rootNav: NavHostController,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    // Detect device type for responsive width
    val screenType = getScreenType()

    // Background wrapper provides consistent page styling
    BackgroundPage(isLanding = false) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            // Scrollable column so content fits on small screens
            Column(
                modifier = Modifier
                    .fillMaxWidth(
                        if (screenType == ScreenType.TABLET) 0.70f else 1f
                    )
                    .verticalScroll(rememberScrollState())
            ) {

                // ---------------------------------------------------------
                // PROFILE INFO CARD
                // Shows basic user info (currently demo/static values)
                // ---------------------------------------------------------
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        // Section title
                        Text(
                            "PROFILE",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(Modifier.height(10.dp))

                        // Demo profile details (replace with real user data if available)
                        Text("Name: Sample User", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text("Email: sampleuser@listify.com")
                    }
                }

                Spacer(Modifier.height(12.dp))

                // ---------------------------------------------------------
                // THEME CARD
                // Allows user to select app theme (System/Light/Dark)
                // The selected option calls onThemeModeChange
                // ---------------------------------------------------------
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("THEME", fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(10.dp))

                        // Follow system theme
                        ThemeRadioRow("Follow System", themeMode == ThemeMode.SYSTEM) {
                            onThemeModeChange(ThemeMode.SYSTEM)
                        }

                        // Force light theme
                        ThemeRadioRow("Light Mode", themeMode == ThemeMode.LIGHT) {
                            onThemeModeChange(ThemeMode.LIGHT)
                        }

                        // Force dark theme
                        ThemeRadioRow("Dark Mode", themeMode == ThemeMode.DARK) {
                            onThemeModeChange(ThemeMode.DARK)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Spacer(Modifier.height(12.dp))

                // ---------------------------------------------------------
                // SETTINGS CARD
                // Contains small app settings (notifications + version)
                // ---------------------------------------------------------
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("SETTINGS", fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(10.dp))

                        // Notifications toggle (currently hardcoded to true as demo)
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Enable Notifications")
                            Switch(
                                checked = true,
                                onCheckedChange = { /* Add notification preference handling */ }
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        // App version display (static value)
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("App Version")
                            Text("1.0.0", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // ---------------------------------------------------------
                // LOGOUT BUTTON
                // Clears session data and redirects to user login
                // ---------------------------------------------------------
                Button(
                    onClick = {
                        AuthManager.logout()
                        rootNav.navigate("${Routes.LOGIN}?admin=false") {
                            // Keeps landing screen in back stack (not removed)
                            popUpTo(Routes.LANDING) { inclusive = false }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("LOG OUT")
                }

                Spacer(Modifier.height(20.dp))

                // Footer navigation links
                FooterLinks()
            }
        }
    }
}

// ---------------------------------------------------------
// THEME RADIO ROW COMPONENT
// Reusable row with label + radio button for theme selection
// ---------------------------------------------------------
@Composable
private fun ThemeRadioRow(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        RadioButton(selected = selected, onClick = onSelect)
    }
}

// ---------------------------------------------------------
// FOOTER LINKS COMPONENT
// Simple non-clickable footer links (can be made clickable)
// ---------------------------------------------------------
@Composable
private fun FooterLinks() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("ABOUT", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(10.dp))
        Text("|")
        Spacer(Modifier.width(10.dp))
        Text("CONTACT", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(10.dp))
        Text("|")
        Spacer(Modifier.width(10.dp))
        Text("TERMS", fontWeight = FontWeight.Bold)
    }
}
