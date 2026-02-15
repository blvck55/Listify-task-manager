package com.example.listify.model

data class TaskItem(
    val id: String,
    val title: String,
    val taskSub: String,
    val description: String,
    val dueDate: String,
    val priority: String,
    val reminder: Boolean = false,
    val status: String = "Pending"
)
