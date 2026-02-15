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

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTitle by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf("") }

    BackgroundPage(isLanding = false) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            //  HEADER WITH VISIBLE ICONS
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

            //  Task History Button
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

            //  Responsive layout
            when {
                !isLandscape && !isTablet -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(tasks) { task ->
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
                        item { Spacer(Modifier.height(80.dp)) }
                    }
                }

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
                                            onDelete = {
                                                selectedTitle = task.title
                                                selectedId = task.id
                                                showDeleteDialog = true
                                            }
                                        )
                                    }
                                }
                                repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                            }
                        }
                        Spacer(Modifier.height(80.dp))
                    }
                }

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
                                            onDelete = {
                                                selectedTitle = task.title
                                                selectedId = task.id
                                                showDeleteDialog = true
                                            }
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

        //  Delete Dialog
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
    }
}
