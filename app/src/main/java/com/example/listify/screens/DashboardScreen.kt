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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isTablet = configuration.screenWidthDp > 600

    // Delete dialog state
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTitle by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf("") }

    //  Complete dialog state
    var showCompleteDialog by remember { mutableStateOf(false) }
    var completeTitle by remember { mutableStateOf("") }
    var completeId by remember { mutableStateOf("") }

    BackgroundPage(isLanding = false) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Header
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

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "MY TASKS",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.width(10.dp))

                        val badgeText = when (AuthManager.role) {
                            UserRole.ADMIN -> "ADMIN"
                            UserRole.USER -> "USER"
                            else -> "GUEST"
                        }

                        RoleBadge(badgeText)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

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

            // History button
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

            val tasks = TaskStore.tasks

            //  helper composable action handlers (used in all layouts)
            fun openDelete(taskId: String, title: String) {
                selectedId = taskId
                selectedTitle = title
                showDeleteDialog = true
            }

            fun openComplete(taskId: String, title: String) {
                completeId = taskId
                completeTitle = title
                showCompleteDialog = true
            }

            when {
                // Phone portrait
                !isLandscape && !isTablet -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(tasks) { task ->
                            TaskCard(
                                task = task,
                                onClick = {
                                    rootNav.navigate("${Routes.TASK_DETAIL}/${task.id}")
                                },
                                onEdit = { goAddEdit() },
                                onDelete = { openDelete(task.id, task.title) },

                                //  User can mark complete
                                onComplete = { openComplete(task.id, task.title) }
                            )
                        }
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }

                // Tablet (3 columns)
                isTablet -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        tasks.chunked(3).forEach { rowItems ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                rowItems.forEach { task ->
                                    Box(modifier = Modifier.weight(1f)) {
                                        TaskCard(
                                            task = task,
                                            onClick = {
                                                rootNav.navigate("${Routes.TASK_DETAIL}/${task.id}")
                                            },
                                            onEdit = { goAddEdit() },
                                            onDelete = { openDelete(task.id, task.title) },

                                            //  User can mark complete
                                            onComplete = { openComplete(task.id, task.title) }
                                        )
                                    }
                                }
                                repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                            }
                        }
                        Spacer(Modifier.height(80.dp))
                    }
                }

                // Landscape phone (2 columns)
                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                                            onDelete = { openDelete(task.id, task.title) },

                                            //  User can mark complete
                                            onComplete = { openComplete(task.id, task.title) }
                                        )
                                    }
                                }
                                if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                        Spacer(Modifier.height(80.dp))
                    }
                }
            }
        }

        // DELETE dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Task") },
                text = { Text("Delete \"$selectedTitle\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        TaskStore.deleteTask(selectedId)
                        showDeleteDialog = false
                    }) { Text("DELETE") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) { Text("CANCEL") }
                }
            )
        }

        //  COMPLETE dialog
        if (showCompleteDialog) {
            AlertDialog(
                onDismissRequest = { showCompleteDialog = false },
                title = { Text("Complete Task") },
                text = { Text("Mark \"$completeTitle\" as completed?") },
                confirmButton = {
                    TextButton(onClick = {
                        TaskStore.completeTask(completeId)
                        showCompleteDialog = false
                    }) { Text("COMPLETE") }
                },
                dismissButton = {
                    TextButton(onClick = { showCompleteDialog = false }) { Text("CANCEL") }
                }
            )
        }
    }
}
