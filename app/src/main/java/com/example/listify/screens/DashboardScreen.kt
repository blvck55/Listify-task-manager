package com.example.listify.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.components.RoleBadge
import com.example.listify.components.TaskCard
import com.example.listify.data.AuthManager
import com.example.listify.data.TaskStore
import com.example.listify.data.UserRole
import com.example.listify.navigation.Routes

@Composable
fun DashboardScreen(
    rootNav: NavHostController,
    goAddEdit: () -> Unit,
    goHistory: () -> Unit
) {
    // Read device configuration to support responsive UI
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600

    // Dialog state: controls whether delete confirmation is visible
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Stores task info for the delete confirmation dialog
    var selectedTitle by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf("") }

    // Background wrapper provides consistent theme/background across screens
    BackgroundPage(isLanding = false) {

        // Main page layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // ---------------------------------------------------------
            // HEADER CARD (Title, Role badge, Admin button, Add button)
            // ---------------------------------------------------------
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Left side: Title + Role badge
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "MY TASKS",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.width(10.dp))

                        // Determine badge text based on current user role
                        val badgeText = when (AuthManager.role) {
                            UserRole.ADMIN -> "ADMIN"
                            UserRole.USER -> "USER"
                            else -> "GUEST"
                        }

                        // Display role badge component
                        RoleBadge(badgeText)
                    }

                    // Right side: Admin icon + Add icon
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        // Admin button:
                        // If admin -> open admin dashboard
                        // If not -> redirect to login page requesting admin access
                        IconButton(onClick = {
                            if (AuthManager.role == UserRole.ADMIN) {
                                rootNav.navigate(Routes.ADMIN)
                            } else {
                                rootNav.navigate("${Routes.LOGIN}?admin=true")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = "Admin",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Add button: go to Add/Edit screen
                        IconButton(onClick = goAddEdit) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Task",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ---------------------------------------------------------
            // TASK HISTORY BUTTON
            // Navigates to a screen that shows completed/history tasks
            // ---------------------------------------------------------
            Button(
                onClick = goHistory,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("TASK HISTORY")
            }

            Spacer(Modifier.height(12.dp))

            // Read tasks from TaskStore (in-memory data source in this project)
            val tasks = TaskStore.tasks

            // ---------------------------------------------------------
            // RESPONSIVE TASK LIST
            // Phone portrait: LazyColumn (1 column)
            // Tablet: 3-column grid style using Rows
            // Landscape phone: 2-column grid style using Rows
            // ---------------------------------------------------------
            when {
                // Phone portrait layout (single column, scrollable)
                !isLandscape && !isTablet -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(tasks) { task ->
                            TaskCard(
                                task = task,

                                // Open task detail screen on card click
                                onClick = {
                                    rootNav.navigate("${Routes.TASK_DETAIL}/${task.id}")
                                },

                                // Edit action (currently navigates to Add/Edit screen)
                                onEdit = { goAddEdit() },

                                // Delete action (opens confirmation dialog)
                                onDelete = {
                                    selectedTitle = task.title
                                    selectedId = task.id
                                    showDeleteDialog = true
                                }
                            )
                        }

                        // Bottom spacing so content does not get hidden by navigation/UI
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }

                // Tablet layout (3 columns)
                isTablet -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        // Chunk tasks in groups of 3 for each row
                        tasks.chunked(3).forEach { rowItems ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                // Render each task as a card in the row
                                rowItems.forEach { task ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        TaskCard(
                                            task = task,
                                            onClick = {
                                                rootNav.navigate("${Routes.TASK_DETAIL}/${task.id}")
                                            },
                                            onEdit = { goAddEdit() },
                                            onDelete = {
                                                selectedTitle = task.title
                                                selectedId = task.id
                                                showDeleteDialog = true
                                            }
                                        )
                                    }
                                }

                                // Add empty spacers if the row has fewer than 3 items
                                repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                            }
                        }

                        Spacer(Modifier.height(80.dp))
                    }
                }

                // Landscape phone layout (2 columns)
                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        // Chunk tasks in groups of 2 for each row
                        tasks.chunked(2).forEach { rowItems ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                                rowItems.forEach { task ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        TaskCard(
                                            task = task,
                                            onClick = {
                                                rootNav.navigate("${Routes.TASK_DETAIL}/${task.id}")
                                            },
                                            onEdit = { goAddEdit() },
                                            onDelete = {
                                                selectedTitle = task.title
                                                selectedId = task.id
                                                showDeleteDialog = true
                                            }
                                        )
                                    }
                                }

                                // If only one item exists in this row, fill the second slot with spacer
                                if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }

                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }

        // ---------------------------------------------------------
        // DELETE CONFIRMATION DIALOG
        // Shows selected task name and confirms deletion
        // ---------------------------------------------------------
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },

                // Dialog title
                title = { Text("Delete Task") },

                // Dialog message with task title
                text = { Text("Delete \"$selectedTitle\"?") },

                // Confirm button deletes task from TaskStore
                confirmButton = {
                    TextButton(onClick = {
                        TaskStore.deleteTask(selectedId)
                        showDeleteDialog = false
                    }) {
                        Text("DELETE")
                    }
                },

                // Cancel button closes dialog without deleting
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("CANCEL")
                    }
                }
            )
        }
    }
}
