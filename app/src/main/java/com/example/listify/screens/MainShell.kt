package com.example.listify.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.listify.navigation.Routes
import com.example.listify.ui.theme.ThemeMode

/**
 * MainShell serves as the main container for the user's logged-in experience.
 * It includes a bottom navigation bar and an inner navigation host to switch between
 * the Dashboard, Add/Edit, History, and Profile screens.
 */
@Composable
fun MainShell(
    rootNav: NavHostController, // Navigation controller for the entire app (e.g., to logout)
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    // Inner navigation controller for bottom bar navigation
    val innerNav = rememberNavController()

    // Observe the current back stack entry to highlight the active bottom bar item
    val currentBackStackEntry by innerNav.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Bottom navigation bar
            NavigationBar {

                // Navigation item for Tasks (Dashboard)
                NavigationBarItem(
                    selected = currentRoute == Routes.DASHBOARD,
                    onClick = {
                        innerNav.navigate(Routes.DASHBOARD) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                        }
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Tasks") }
                )

                // Navigation item for Add/Edit screen
                NavigationBarItem(
                    selected = currentRoute == Routes.ADD_EDIT,
                    onClick = {
                        innerNav.navigate(Routes.ADD_EDIT) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                        }
                    },
                    icon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
                    label = { Text("Add/Edit") }
                )

                // Navigation item for History screen
                NavigationBarItem(
                    selected = currentRoute == Routes.HISTORY,
                    onClick = {
                        innerNav.navigate(Routes.HISTORY) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                        }
                    },
                    icon = { Icon(Icons.Default.AccessTime, contentDescription = null) },
                    label = { Text("History") }
                )

                // Navigation item for Profile screen
                NavigationBarItem(
                    selected = currentRoute == Routes.PROFILE,
                    onClick = {
                        innerNav.navigate(Routes.PROFILE) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                        }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->

        // Inner NavHost for managing the screens within the MainShell
        NavHost(
            navController = innerNav,
            startDestination = Routes.DASHBOARD,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.DASHBOARD) {
                DashboardScreen(
                    rootNav = rootNav,
                    goAddEdit = { innerNav.navigate(Routes.ADD_EDIT) { launchSingleTop = true } },
                    goHistory = { innerNav.navigate(Routes.HISTORY) { launchSingleTop = true } }
                )
            }
            composable(Routes.ADD_EDIT) { AddEditScreen() }
            composable(Routes.HISTORY) { HistoryScreen() }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    rootNav = rootNav,
                    themeMode = themeMode,
                    onThemeModeChange = onThemeModeChange
                )
            }
        }
    }
}
