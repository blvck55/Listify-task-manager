package com.example.listify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listify.screens.*
import com.example.listify.ui.theme.ThemeMode

// ---------------------------------------------------------
// NavGraph (Official Navigation Compose version)
// Removes accompanist AnimatedNavHost deprecated warnings.
// Handles all app routes in one place.
// ---------------------------------------------------------
@Composable
fun NavGraph(
    navController: NavHostController,

    // Current theme mode selected by the user (System/Light/Dark)
    themeMode: ThemeMode,

    // Callback to update theme mode from inside screens (e.g., ProfileScreen)
    onThemeModeChange: (ThemeMode) -> Unit
) {

    // ---------------------------------------------------------
    // NavHost
    // Defines the navigation routes (without accompanist animations).
    // startDestination = first screen shown when app launches.
    // ---------------------------------------------------------
    NavHost(
        navController = navController,
        startDestination = Routes.LANDING
    ) {

        // ---------------------------------------------------------
        // ROUTE: Landing Screen (first screen)
        // ---------------------------------------------------------
        composable(Routes.LANDING) {
            LandingScreen(navController)
        }

        // ---------------------------------------------------------
        // ROUTE: Login Screen with query param admin=true/false
        // Example: /login?admin=true
        // ---------------------------------------------------------
        composable(
            route = "${Routes.LOGIN}?admin={admin}",
            arguments = listOf(
                navArgument("admin") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            val admin = entry.arguments?.getBoolean("admin") ?: false
            LoginScreen(navController, adminMode = admin)
        }

        // ---------------------------------------------------------
        // ROUTE: Register Screen
        // ---------------------------------------------------------
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        // ---------------------------------------------------------
        // ROUTE: MainShell
        // Contains bottom navigation pages (Dashboard/Profile/etc.)
        // Theme controls are passed so Profile can update theme.
        // ---------------------------------------------------------
        composable(Routes.MAIN) {
            MainShell(
                rootNav = navController,
                themeMode = themeMode,
                onThemeModeChange = onThemeModeChange
            )
        }

        // ---------------------------------------------------------
        // ROUTE: Task Detail Screen
        // Uses a path argument {taskId}
        // Example: /task_detail/123
        // ---------------------------------------------------------
        composable(
            route = "${Routes.TASK_DETAIL}/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(navController, taskId)
        }

        // ---------------------------------------------------------
        // ROUTE: Admin Dashboard Screen
        // ---------------------------------------------------------
        composable(Routes.ADMIN) {
            AdminDashboardScreen(navController)
        }

        // ---------------------------------------------------------
        // ROUTE: System Report Screen (Admin feature)
        // ---------------------------------------------------------
        composable(Routes.REPORT) {
            SystemReportScreen(navController)
        }
    }
}
