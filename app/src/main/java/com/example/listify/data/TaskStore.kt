package com.example.listify.data

import androidx.compose.runtime.mutableStateListOf
import com.example.listify.model.TaskItem
import java.util.UUID

// ---------------------------------------------------------
// TaskStore (In-Memory Data Source)
// Acts as a simple state holder for tasks.
// Used across:
// - DashboardScreen
// - AdminDashboardScreen
// - HistoryScreen
// - SystemReportScreen
//
// NOTE: This is not persistent storage.
// Data will reset when the app restarts.
// ---------------------------------------------------------
object TaskStore {

    // ---------------------------------------------------------
    // Active tasks list
    // mutableStateListOf ensures Compose recomposes UI
    // automatically when the list changes.
    // ---------------------------------------------------------
    val tasks = mutableStateListOf<TaskItem>()

    // ---------------------------------------------------------
    // Completed tasks list (history)
    // When a task is marked completed, it moves here.
    // ---------------------------------------------------------
    val history = mutableStateListOf<TaskItem>()

    // ---------------------------------------------------------
    // addTask()
    // Creates a new task and adds it to active task list.
    // Generates a unique ID using UUID.
    // ---------------------------------------------------------
    fun addTask(
        title: String,
        taskSub: String,
        description: String,
        dueDate: String,
        priority: String,
        reminder: Boolean
    ) {
        tasks.add(
            TaskItem(
                id = UUID.randomUUID().toString(),  // Unique identifier
                title = title,
                taskSub = taskSub,
                description = description,
                dueDate = dueDate,
                priority = priority,
                reminder = reminder,
                status = "Pending"                 // Default task status
            )
        )
    }

    // ---------------------------------------------------------
    // completeTask()
    // Moves task from active list to history list.
    // Updates status to "Completed".
    // ---------------------------------------------------------
    fun completeTask(id: String) {

        // Find task in active list
        val task = tasks.find { it.id == id }

        task?.let {
            // Remove from active tasks
            tasks.remove(it)

            // Add to history with updated status
            history.add(it.copy(status = "Completed"))
        }
    }

    // ---------------------------------------------------------
    // deleteTask()
    // Permanently removes task from active tasks list.
    // (Does not affect history list.)
    // ---------------------------------------------------------
    fun deleteTask(id: String) {
        tasks.removeAll { it.id == id }
    }
}
