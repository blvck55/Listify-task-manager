package com.example.listify.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.R
import com.example.listify.components.BackgroundPage
import com.example.listify.navigation.Routes

@Composable
fun LandingScreen(nav: NavHostController) {

    BackgroundPage(isLanding = true) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //  Glass panel
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.60f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.listify_logo),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "LISTIFY",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Organize tasks. Track progress. Stay consistent.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(18.dp))

                    Button(
                        onClick = { nav.navigate("${Routes.LOGIN}?admin=false") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text("GET STARTED") }

                    Spacer(Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = { nav.navigate(Routes.REGISTER) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) { Text("REGISTER") }

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "For Administrators",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { nav.navigate("${Routes.LOGIN}?admin=true") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) { Text("ADMIN LOGIN") }
                }
            }
        }
    }
}
