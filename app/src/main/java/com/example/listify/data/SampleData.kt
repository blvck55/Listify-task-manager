package com.example.listify.data

import com.example.listify.model.TaskItem


// SAMPLE DATA
// These lists provide pre-defined tasks for:
// - TaskDetailScreen
// - Demo
// - Initial UI testing
//
// NOTE:
// This data is static and not connected to TaskStore.
// It is mainly used for demonstration purposes.

// SAMPLE ACTIVE TASKS
// Used to simulate real dashboard data

val sampleTasks = listOf(

    TaskItem(
        id = "T1",                                  // Unique task ID
        title = "Finish UI Screens",
        dueDate = "2026-02-20",
        taskSub = "Compose layout",
        description = "Build all screens based on Listify mockups with navigation and dark mode.",
        priority = "High",
        reminder = true,
        status = "Active"                           // Current task state
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

// ---------------------------------------------------------
// SAMPLE COMPLETED TASKS (History)
// Used to simulate completed task history
// ---------------------------------------------------------
val sampleHistory = listOf(

    TaskItem(
        id = "H1",
        title = "Design Mockups",
        dueDate = "2026-02-10",
        taskSub = "UI planning",
        description = "Completed landing, login, register, dashboard, history, add/edit mockups.",
        priority = "Medium",
        reminder = false,
        status = "Completed"                        // Completed state
    )
)
