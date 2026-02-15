package com.example.listify.model

// ---------------------------------------------------------
// TaskItem Data Model
// Represents a single task object in the system.
// Used by:
// - DashboardScreen
// - AdminDashboardScreen
// - HistoryScreen
// - TaskDetailScreen
// - TaskStore
// ---------------------------------------------------------
data class TaskItem(

    // Unique identifier for each task
    // Used for navigation (e.g., TaskDetailScreen)
    val id: String,

    // Main task title (required field)
    val title: String,

    // Optional subtitle or short description
    val taskSub: String,

    // Detailed description of the task
    val description: String,

    // Due date stored as String (e.g., "2026-02-20")
    // Can be converted to LocalDate in future for better handling
    val dueDate: String,

    // Task priority (Low / Medium / High)
    val priority: String,

    // Indicates whether reminder is enabled
    val reminder: Boolean = false,

    // Task status (default is "Pending")
    // Changes to "Completed" when task is finished
    val status: String = "Pending"
)
