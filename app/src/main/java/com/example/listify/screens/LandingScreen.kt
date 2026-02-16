package com.example.listify.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.R
import com.example.listify.components.BackgroundPage
import com.example.listify.data.TaskStore
import com.example.listify.navigation.Routes

@Composable
fun LandingScreen(nav: NavHostController) {



    BackgroundPage(isLanding = true) {


        // Main layout: centered content with padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------------------------------------------------------
            // Main Glass Panel Card (contains logo, title, and buttons)
            // ---------------------------------------------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    // Transparent/glass effect by reducing alpha
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.60f)
                )
            ) {

                // Card content layout
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // App logo image (stored in drawable resources)
                    Image(
                        painter = painterResource(R.drawable.listify_logo),
                        contentDescription = null, // Decorative logo (no accessibility label needed)
                        modifier = Modifier.size(90.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    // App name / branding title
                    Text(
                        text = "LISTIFY",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(6.dp))

                    // Tagline text to describe app purpose
                    Text(
                        text = "Organize tasks. Track progress. Stay consistent.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(18.dp))

                    // ---------------------------------------------------------
                    // GET STARTED button (navigates to user login)
                    // admin=false indicates normal user login flow
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
                        Text("GET STARTED")
                    }

                    Spacer(Modifier.height(10.dp))

                    // ---------------------------------------------------------
                    // REGISTER button (navigates to register screen)
                    // ---------------------------------------------------------
                    OutlinedButton(
                        onClick = { nav.navigate(Routes.REGISTER) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("REGISTER")
                    }

                    Spacer(Modifier.height(14.dp))

                    // Section label for admin login option
                    Text(
                        text = "For Administrators",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(8.dp))

                    // ---------------------------------------------------------
                    // ADMIN LOGIN button (navigates to log in screen with admin=true)
                    // admin=true can be used to show admin-specific login behavior
                    // ---------------------------------------------------------
                    OutlinedButton(
                        onClick = { nav.navigate("${Routes.LOGIN}?admin=true") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("ADMIN LOGIN")
                    }
                }
            }
        }
    }
}
