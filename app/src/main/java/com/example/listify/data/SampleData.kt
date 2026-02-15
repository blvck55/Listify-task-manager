package com.example.listify.data

import com.example.listify.model.TaskItem

val sampleTasks = listOf(
    TaskItem(
        id = "T1",
        title = "Finish UI Screens",
        dueDate = "2026-02-20",
        taskSub = "Compose layout",
        description = "Build all screens based on Listify mockups with navigation and dark mode.",
        priority = "High",
        reminder = true,
        status = "Active"
    ),
    TaskItem(
        id = "T2",
        title = "Prepare 15-min Demo",
        dueDate = "2026-02-22",
        taskSub = "Presentation",
        description = "Show login/register, bottom nav, master/detail, rotation, dark mode, micro interaction.",
        priority = "Medium",
        reminder = true,
        status = "Active"
    ),
    TaskItem(
        id = "T3",
        title = "Commit to GitHub",
        dueDate = "2026-02-23",
        taskSub = "Version control",
        description = "Commit frequently with clear messages; no commits after deadline.",
        priority = "Low",
        reminder = false,
        status = "Active"
    )
)

val sampleHistory = listOf(
    TaskItem(
        id = "H1",
        title = "Design Mockups",
        dueDate = "2026-02-10",
        taskSub = "UI planning",
        description = "Completed landing, login, register, dashboard, history, add/edit mockups.",
        priority = "Medium",
        reminder = false,
        status = "Completed"
    )
)
