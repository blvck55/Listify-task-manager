package com.example.listify.data

import androidx.compose.runtime.mutableStateListOf
import com.example.listify.model.TaskItem
import java.util.UUID

object TaskStore {

    val tasks = mutableStateListOf<TaskItem>()
    val history = mutableStateListOf<TaskItem>()

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
                id = UUID.randomUUID().toString(),
                title = title,
                taskSub = taskSub,
                description = description,
                dueDate = dueDate,
                priority = priority,
                reminder = reminder,
                status = "Pending"
            )
        )
    }

    fun completeTask(id: String) {
        val task = tasks.find { it.id == id }
        task?.let {
            tasks.remove(it)
            history.add(it.copy(status = "Completed"))
        }
    }

    fun deleteTask(id: String) {
        tasks.removeAll { it.id == id }
    }
}
