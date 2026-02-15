package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.data.sampleHistory
import com.example.listify.data.sampleTasks

@Composable
fun TaskDetailScreen(nav: NavHostController, taskId: String) {

    val task = (sampleTasks + sampleHistory).find { it.id == taskId }

    BackgroundPage(isLanding = false) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {

            Row {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.width(6.dp))
                Text("TASK DETAIL", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
            }

            Spacer(Modifier.height(14.dp))

            if (task == null) {
                Text("Task not found.")
                return@BackgroundPage
            }

            Card(shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text(task.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Task Sub: ${task.taskSub}")
                    Spacer(Modifier.height(6.dp))
                    Text("Description: ${task.description}")
                    Spacer(Modifier.height(6.dp))
                    Text("Due Date: ${task.dueDate}")
                    Spacer(Modifier.height(6.dp))
                    Text("Priority: ${task.priority}")
                    Spacer(Modifier.height(6.dp))
                    Text("Status: ${task.status}")
                }
            }
        }
    }
}
