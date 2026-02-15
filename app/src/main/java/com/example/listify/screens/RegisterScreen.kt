package com.example.listify.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.R
import com.example.listify.components.BackgroundPage
import com.example.listify.navigation.Routes
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType

@Composable
fun RegisterScreen(nav: NavHostController) {

    // Detect device type for responsive layout adjustments (tablet vs phone)
    val screenType = getScreenType()

    // ---------------------------------------------------------
    // Form input states
    // ---------------------------------------------------------
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ---------------------------------------------------------
    // Shared OutlinedTextField color styling (glass theme)
    // ---------------------------------------------------------
    val tfColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        cursorColor = MaterialTheme.colorScheme.primary
    )

    // Background wrapper for consistent screen styling
    BackgroundPage(isLanding = false) {

        // Center the register card on the screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {

            // Glass-style card container (responsive width on tablet)
            Card(
                modifier = Modifier.fillMaxWidth(
                    if (screenType == ScreenType.TABLET) 0.55f else 1f
                ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.60f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // App logo (drawable resource)
                    Image(
                        painter = painterResource(R.drawable.listify_logo),
                        contentDescription = null, // Decorative logo
                        modifier = Modifier.size(70.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    // Screen heading/title
                    Text(
                        text = "CREATE\nAN\nACCOUNT",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(12.dp))

                    // ---------------------------------------------------------
                    // EMAIL FIELD
                    // ---------------------------------------------------------
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("EMAIL") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = tfColors
                    )

                    Spacer(Modifier.height(10.dp))

                    // ---------------------------------------------------------
                    // FULL NAME FIELD
                    // ---------------------------------------------------------
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("FULL NAME") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = tfColors
                    )

                    Spacer(Modifier.height(10.dp))

                    // ---------------------------------------------------------
                    // PASSWORD FIELD (masked input)
                    // ---------------------------------------------------------
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("PASSWORD") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = tfColors
                    )

                    Spacer(Modifier.height(14.dp))

                    // ---------------------------------------------------------
                    // CREATE ACCOUNT BUTTON
                    // For demo: navigates directly to login screen after "creating"
                    // In a real app: validate inputs + save to database/auth service
                    // ---------------------------------------------------------
                    Button(
                        onClick = { nav.navigate("${Routes.LOGIN}?admin=false") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("CREATE ACCOUNT")
                    }

                    Spacer(Modifier.height(10.dp))

                    // ---------------------------------------------------------
                    // LOGIN LINK
                    // Redirects user back to login screen
                    // ---------------------------------------------------------
                    TextButton(onClick = { nav.navigate("${Routes.LOGIN}?admin=false") }) {
                        Text("Already have an account? LOGIN")
                    }
                }
            }
        }
    }
}
