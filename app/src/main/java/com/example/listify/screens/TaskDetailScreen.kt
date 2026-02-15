package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.data.sampleHistory
import com.example.listify.data.sampleTasks

@Composable
fun TaskDetailScreen(nav: NavHostController, taskId: String) {

    // ---------------------------------------------------------
    // Find the task by ID from:
    // 1) Active tasks list (sampleTasks)
    // 2) Completed/history list (sampleHistory)
    // This allows showing details even if the task is completed.
    // ---------------------------------------------------------
    val task = (sampleTasks + sampleHistory).find { it.id == taskId }

    // Background wrapper for consistent app theme styling
    BackgroundPage(isLanding = false) {

        // Main screen layout container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // ---------------------------------------------------------
            // Header Row (Back button + Title)
            // Uses AutoMirrored back icon for RTL support (no deprecation)
            // ---------------------------------------------------------
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Back navigation button
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(Modifier.width(6.dp))

                // Screen title
                Text(
                    text = "TASK DETAIL",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(Modifier.height(14.dp))

            // ---------------------------------------------------------
            // If task is not found, show message and stop rendering
            // ---------------------------------------------------------
            if (task == null) {
                Text("Task not found.")
                return@BackgroundPage
            }

            // ---------------------------------------------------------
            // Task Detail Card
            // Displays all properties of the selected task
            // ---------------------------------------------------------
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    // Slight glass effect to match other screens
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    // Task main title (most important field)
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    // Task subtitle (optional)
                    Text("Task Sub: ${task.taskSub}")

                    Spacer(Modifier.height(6.dp))

                    // Task description (optional)
                    Text("Description: ${task.description}")

                    Spacer(Modifier.height(6.dp))

                    // Due date text
                    Text("Due Date: ${task.dueDate}")

                    Spacer(Modifier.height(6.dp))

                    // Priority text (Low/Medium/High)
                    Text("Priority: ${task.priority}")

                    Spacer(Modifier.height(6.dp))

                    // Status text (Pending/Completed or your defined values)
                    Text("Status: ${task.status}")
                }
            }
        }
    }
}
