package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listify.components.BackgroundPage
import com.example.listify.data.TaskStore
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType
import kotlinx.coroutines.launch

@Composable
fun AddEditScreen() {

    // Detect whether device is phone or tablet for responsive layout
    val screenType = getScreenType()

    // -----------------------------
    // State variables for form data
    // -----------------------------
    var title by remember { mutableStateOf("") }
    var taskSub by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("2026-02-20") }

    // Dropdown state for priority
    var priority by remember { mutableStateOf("Medium") }
    var expanded by remember { mutableStateOf(false) }

    // Reminder switch state
    var reminderOn by remember { mutableStateOf(true) }

    // Snackbar for user feedback
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ---------------------------------
    // Function to reset/clear the form
    // ---------------------------------
    fun resetForm() {
        title = ""
        taskSub = ""
        description = ""
        dueDate = "2026-02-20"
        priority = "Medium"
        reminderOn = true
        expanded = false
    }

    // ---------------------------------
    // Custom TextField color styling
    // ---------------------------------
    val tfColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        cursorColor = MaterialTheme.colorScheme.primary
    )

    // Background wrapper for consistent UI theme
    BackgroundPage(isLanding = false) {

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                // Card container for form layout
                Card(
                    modifier = Modifier.fillMaxWidth(
                        if (screenType == ScreenType.TABLET) 0.70f else 1f
                    ),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.60f)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {

                        // Screen title
                        Text(
                            text = "ADD AND EDIT TASKS",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Modifier.height(12.dp))

                        // -----------------------------
                        // TITLE FIELD
                        // -----------------------------
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("TITLE") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = tfColors
                        )

                        Spacer(Modifier.height(10.dp))

                        // -----------------------------
                        // TASK SUBTITLE FIELD
                        // -----------------------------
                        OutlinedTextField(
                            value = taskSub,
                            onValueChange = { taskSub = it },
                            label = { Text("TASK SUB") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = tfColors
                        )

                        Spacer(Modifier.height(10.dp))

                        // -----------------------------
                        // DESCRIPTION FIELD (multiline)
                        // -----------------------------
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("DESCRIPTION") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            colors = tfColors
                        )

                        Spacer(Modifier.height(10.dp))

                        // -----------------------------
                        // DUE DATE FIELD
                        // -----------------------------
                        OutlinedTextField(
                            value = dueDate,
                            onValueChange = { dueDate = it },
                            label = { Text("DUE DATE (YYYY-MM-DD)") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            colors = tfColors
                        )

                        Spacer(Modifier.height(10.dp))

                        // -----------------------------
                        // PRIORITY DROPDOWN
                        // -----------------------------
                        Box {
                            OutlinedTextField(
                                value = priority,
                                onValueChange = {},
                                readOnly = true, // prevents manual typing
                                label = { Text("PRIORITY") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = tfColors,
                                trailingIcon = {
                                    IconButton(onClick = { expanded = true }) {
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = "Priority"
                                        )
                                    }
                                }
                            )

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                listOf("Low", "Medium", "High").forEach {
                                    DropdownMenuItem(
                                        text = { Text(it) },
                                        onClick = {
                                            priority = it
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        // -----------------------------
                        // REMINDER SWITCH
                        // -----------------------------
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.70f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("REMINDER")
                                Switch(
                                    checked = reminderOn,
                                    onCheckedChange = { reminderOn = it }
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // -----------------------------
                        // BUTTONS: CANCEL & SAVE
                        // -----------------------------
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            // CANCEL BUTTON
                            OutlinedButton(
                                onClick = {
                                    resetForm()
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Cancelled and cleared form"
                                        )
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("CANCEL")
                            }

                            Spacer(Modifier.width(12.dp))

                            // SAVE BUTTON
                            Button(
                                onClick = {

                                    // Validate title before saving
                                    val safeTitle = title.trim()
                                    if (safeTitle.isBlank()) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Please enter a task title"
                                            )
                                        }
                                        return@Button
                                    }

                                    // Save task into in-memory TaskStore
                                    TaskStore.addTask(
                                        title = safeTitle,
                                        taskSub = taskSub.trim(),
                                        description = description.trim(),
                                        dueDate = dueDate.trim(),
                                        priority = priority,
                                        reminder = reminderOn
                                    )

                                    // Clear form after saving
                                    resetForm()

                                    // Show confirmation
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Task saved to Dashboard (demo)"
                                        )
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("SAVE")
                            }
                        }
                    }
                }
            }
        }
    }
}
