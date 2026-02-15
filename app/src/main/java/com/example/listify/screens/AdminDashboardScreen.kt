package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.data.AuthManager
import com.example.listify.data.TaskStore
import com.example.listify.data.UserRole
import com.example.listify.navigation.Routes
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType

@Composable
fun AdminDashboardScreen(nav: NavHostController) {

    // Detect device type for responsive layout adjustments
    val screenType = getScreenType()

    // ------------------------------------------
    // Security check: Allow only ADMIN users
    // ------------------------------------------
    LaunchedEffect(Unit) {
        if (AuthManager.role != UserRole.ADMIN) {
            nav.navigate("${Routes.LOGIN}?admin=true") {
                popUpTo(Routes.ADMIN) { inclusive = true }
            }
        }
    }

    // Prevent UI rendering if user is not admin
    if (AuthManager.role != UserRole.ADMIN) return

    BackgroundPage(isLanding = false) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(
                    if (screenType == ScreenType.TABLET) 0.80f else 1f
                )
            ) {

                // ------------------------------------------
                // Header Section with Back Navigation
                // ------------------------------------------
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Back button navigation
                        IconButton(onClick = { nav.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )

                        }

                        // Screen title
                        Text(
                            "ADMIN DASHBOARD",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // ------------------------------------------
                // Statistics Section
                // Displays total and completed tasks
                // ------------------------------------------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "TOTAL TASKS",
                        value = TaskStore.tasks.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "COMPLETED",
                        value = TaskStore.history.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(12.dp))

                // ------------------------------------------
                // Manage Tasks Section Header
                // ------------------------------------------
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.70f)
                    )
                ) {
                    Text(
                        text = "MANAGE TASKS",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(Modifier.height(10.dp))

                // ------------------------------------------
                // Task List Section
                // Displays all active tasks with actions
                // ------------------------------------------
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {

                    // Loop through all active tasks
                    items(TaskStore.tasks) { task ->

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.78f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.padding(14.dp)) {

                                // Task title
                                Text(
                                    task.title,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                // Due date display
                                Text(
                                    "Due: ${task.dueDate}",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                                )

                                Spacer(Modifier.height(10.dp))

                                // ------------------------------------------
                                // Action Buttons: Complete & Delete
                                // ------------------------------------------
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    // Mark task as completed
                                    OutlinedButton(
                                        onClick = {
                                            TaskStore.completeTask(task.id)
                                        },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                                        Spacer(Modifier.width(6.dp))
                                        Text("Complete")
                                    }

                                    // Delete task permanently
                                    OutlinedButton(
                                        onClick = {
                                            TaskStore.deleteTask(task.id)
                                        },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                        Spacer(Modifier.width(6.dp))
                                        Text("Delete")
                                    }
                                }
                            }
                        }
                    }

                    item { Spacer(Modifier.height(10.dp)) }
                }

                Spacer(Modifier.height(12.dp))

                // ------------------------------------------
                // Logout Button
                // Clears session and redirects to login
                // ------------------------------------------
                Button(
                    onClick = {
                        AuthManager.logout()
                        nav.navigate("${Routes.LOGIN}?admin=true") {
                            popUpTo(Routes.LANDING) { inclusive = false }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("LOG OUT ADMIN")
                }
            }
        }
    }
}

// ------------------------------------------
// Reusable Statistics Card Component
// Used to display dashboard metrics
// ------------------------------------------
@Composable
private fun StatCard(title: String, value: String, modifier: Modifier) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.80f)
        )
    ) {

        Column(Modifier.padding(14.dp)) {

            // Title of statistic
            Text(
                title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(6.dp))

            // Numeric value of statistic
            Text(
                value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
