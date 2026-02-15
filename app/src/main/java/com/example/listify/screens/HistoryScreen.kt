package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.listify.components.BackgroundPage
import com.example.listify.data.TaskStore
import com.example.listify.model.TaskItem
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType

@Composable
fun HistoryScreen() {

    // Detect device type for responsive layout
    val screenType = getScreenType()

    // Stores current filter mode (All or Completed)
    var filterMode by remember { mutableStateOf("All") }

    // Controls visibility of filter dialog
    var showFilter by remember { mutableStateOf(false) }

    // Determine which items to display based on selected filter
    val itemsToShow = when (filterMode) {
        "Completed" -> TaskStore.history.filter { it.status == "Completed" }
        else -> TaskStore.history
    }

    // Background wrapper for consistent UI styling
    BackgroundPage(isLanding = false) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(
                    if (screenType == ScreenType.TABLET) 0.75f else 1f
                )
            ) {

                // --------------------------------------------------
                // HEADER SECTION (Title + Filter Icon)
                // --------------------------------------------------
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                    )
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Screen title
                        Text(
                            "HISTORY",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // Filter button opens filter dialog
                        IconButton(onClick = { showFilter = true }) {
                            Icon(
                                Icons.Default.FilterList,
                                contentDescription = "Filter",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // --------------------------------------------------
                // TASK HISTORY LIST
                // Displays filtered history items
                // --------------------------------------------------
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(itemsToShow) { task ->
                        HistoryCard(task)
                    }

                    // Bottom spacing to prevent overlap with footer
                    item { Spacer(Modifier.height(60.dp)) }
                }

                Spacer(Modifier.height(12.dp))

                // Footer navigation links
                FooterLinks()
            }
        }

        // --------------------------------------------------
        // FILTER DIALOG
        // Allows user to filter history list
        // --------------------------------------------------
        if (showFilter) {
            AlertDialog(
                onDismissRequest = { showFilter = false },

                // Dialog title
                title = { Text("Filter") },

                // Filter options (Radio buttons)
                text = {
                    Column {
                        FilterOption("All", filterMode == "All") {
                            filterMode = "All"
                        }
                        FilterOption("Completed", filterMode == "Completed") {
                            filterMode = "Completed"
                        }
                    }
                },

                // Confirm button closes dialog
                confirmButton = {
                    TextButton(onClick = { showFilter = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

// --------------------------------------------------
// HISTORY CARD COMPONENT
// Displays individual completed task details
// --------------------------------------------------
@Composable
private fun HistoryCard(task: TaskItem) {

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {

            // Task Name Section
            Text(
                "TASK NAME",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.title, color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(6.dp))

            // Date Completed Section
            Text(
                "DATE COMPLETED",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.dueDate, color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(6.dp))

            // Status Section
            Text(
                "STATUS",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.status, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

// --------------------------------------------------
// FILTER OPTION COMPONENT
// Reusable radio button row for filter selection
// --------------------------------------------------
@Composable
private fun FilterOption(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        RadioButton(selected = selected, onClick = onSelect)
    }
}

// --------------------------------------------------
// FOOTER LINKS COMPONENT
// Simple informational links at bottom of screen
// --------------------------------------------------
@Composable
private fun FooterLinks() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text("ABOUT", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(10.dp))
        Text("|")
        Spacer(Modifier.width(10.dp))
        Text("CONTACT", fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(10.dp))
        Text("|")
        Spacer(Modifier.width(10.dp))
        Text("TERMS", fontWeight = FontWeight.Bold)
    }
}
