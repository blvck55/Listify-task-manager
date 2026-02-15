package com.example.listify.screens

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.R
import com.example.listify.components.BackgroundPage
import com.example.listify.data.AuthManager
import com.example.listify.navigation.Routes
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavHostController, adminMode: Boolean) {

    // Stores error message shown under the password field
    var error by remember { mutableStateOf("") }

    // Form input states
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Detect device type for responsive card width
    val screenType = getScreenType()

    // ---------------------------------------------------------
    // Screen entrance animation: delays then shows content
    // ---------------------------------------------------------
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(150)
        showContent = true
    }

    // ---------------------------------------------------------
    // Logo floating animation (continuous micro-interaction)
    // ---------------------------------------------------------
    val infinite = rememberInfiniteTransition(label = "logoFloat")
    val logoOffset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoOffset"
    )

    // ---------------------------------------------------------
    // Button press micro-animation: scales slightly when pressed
    // ---------------------------------------------------------
    var pressed by remember { mutableStateOf(false) }
    val buttonScale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(120, easing = FastOutSlowInEasing),
        label = "btnScale"
    )
    val scope = rememberCoroutineScope()

    // ---------------------------------------------------------
    // Shared OutlinedTextField colors (glass style + consistent UI)
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

    // Background wrapper for consistent theme/styling
    BackgroundPage(isLanding = false) {

        // Center the login card in the screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {

            // AnimatedVisibility adds fade + slide entrance transition
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(350)) + slideInVertically(
                    initialOffsetY = { it / 4 },
                    animationSpec = tween(350, easing = FastOutSlowInEasing)
                )
            ) {

                // Glass-style login panel
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

                        // App logo (with floating animation offset)
                        Image(
                            painter = painterResource(R.drawable.listify_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .offset(y = (-logoOffset).dp)
                        )

                        Spacer(Modifier.height(10.dp))

                        // Title changes depending on admin/user mode
                        Text(
                            text = if (adminMode) "ADMIN\nLOGIN" else "WELCOME\nBACK!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.height(14.dp))

                        // ---------------------------------------------------------
                        // Email input field
                        // Clears error when user starts typing again
                        // ---------------------------------------------------------
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it; error = "" },
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            colors = tfColors
                        )

                        Spacer(Modifier.height(10.dp))

                        // ---------------------------------------------------------
                        // Password input field (hidden text)
                        // Clears error when user starts typing again
                        // ---------------------------------------------------------
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; error = "" },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = tfColors
                        )

                        // Show validation or login errors
                        if (error.isNotBlank()) {
                            Spacer(Modifier.height(10.dp))
                            Text(error, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(Modifier.height(14.dp))

                        // ---------------------------------------------------------
                        // Login button
                        // Performs input validation + calls AuthManager login
                        // ---------------------------------------------------------
                        Button(
                            onClick = {
                                // Read safe/trimmed inputs
                                val e = email.trim()
                                val p = password.trim()

                                // Validate email format
                                if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                                    error = "Enter a valid email address"
                                    return@Button
                                }

                                // Validate password length (basic rule for demo)
                                if (p.length < 6) {
                                    error = "Password must be at least 6 characters"
                                    return@Button
                                }

                                // Trigger button press animation feedback
                                pressed = true
                                scope.launch { delay(90); pressed = false }

                                // Perform role-based credential validation
                                if (adminMode) {
                                    val ok = AuthManager.loginAdmin(e, p)
                                    if (ok) {
                                        nav.navigate(Routes.ADMIN)
                                    } else {
                                        error = "Invalid admin credentials (admin@listify.com / Admin123)"
                                    }
                                } else {
                                    val ok = AuthManager.loginUser(e, p)
                                    if (ok) {
                                        nav.navigate(Routes.MAIN)
                                    } else {
                                        error = "Invalid user credentials (user@listify.com / User123)"
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(buttonScale),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(if (adminMode) "LOGIN AS ADMIN" else "LOGIN")
                        }

                        Spacer(Modifier.height(10.dp))

                        // ---------------------------------------------------------
                        // Navigation link under the button
                        // User mode -> go to Register
                        // Admin mode -> go back to User login
                        // ---------------------------------------------------------
                        if (!adminMode) {
                            TextButton(onClick = { nav.navigate(Routes.REGISTER) }) {
                                Text("Don’t have an account? REGISTER")
                            }
                        } else {
                            TextButton(onClick = { nav.navigate("${Routes.LOGIN}?admin=false") }) {
                                Text("Go to User Login")
                            }
                        }

                        // ---------------------------------------------------------
                        // Demo helper line to show test credentials
                        // ---------------------------------------------------------
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = if (adminMode)
                                "Demo: admin@listify.com / Admin123"
                            else
                                "Demo: user@listify.com / User123",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                    }
                }
            }
        }
    }
}
