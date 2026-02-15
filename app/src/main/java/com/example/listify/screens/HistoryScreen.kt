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

    val screenType = getScreenType()

    var filterMode by remember { mutableStateOf("All") }
    var showFilter by remember { mutableStateOf(false) }

    val itemsToShow = when (filterMode) {
        "Completed" -> TaskStore.history.filter { it.status == "Completed" }
        else -> TaskStore.history
    }

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

                // HEADER CARD
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
                        Text(
                            "HISTORY",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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

                // TASK LIST
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(itemsToShow) { task ->
                        HistoryCard(task)
                    }
                    item { Spacer(Modifier.height(60.dp)) }
                }

                Spacer(Modifier.height(12.dp))

                FooterLinks()
            }
        }

        // FILTER DIALOG
        if (showFilter) {
            AlertDialog(
                onDismissRequest = { showFilter = false },
                title = { Text("Filter") },
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
                confirmButton = {
                    TextButton(onClick = { showFilter = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

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

            Text(
                "TASK NAME",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.title, color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(6.dp))

            Text(
                "DATE COMPLETED",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.dueDate, color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(6.dp))

            Text(
                "STATUS",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(task.status, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun FilterOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        RadioButton(selected = selected, onClick = onSelect)
    }
}

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
