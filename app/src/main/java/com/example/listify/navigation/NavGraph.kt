package com.example.listify.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.example.listify.screens.*
import com.example.listify.ui.theme.ThemeMode
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Routes.LANDING,

        //  Forward navigation animation (push)
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, // from right
                animationSpec = tween(260)
            ) + fadeIn(animationSpec = tween(260))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 3 }, // to left slightly
                animationSpec = tween(220)
            ) + fadeOut(animationSpec = tween(220))
        },

        //  Back navigation animation (pop)
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 3 }, // from left slightly
                animationSpec = tween(220)
            ) + fadeIn(animationSpec = tween(220))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, // to right
                animationSpec = tween(260)
            ) + fadeOut(animationSpec = tween(260))
        }
    ) {
        composable(Routes.REPORT) {
            SystemReportScreen(navController)
        }


        composable(Routes.LANDING) {
            LandingScreen(navController)
        }

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

        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(Routes.MAIN) {
            MainShell(
                rootNav = navController,
                themeMode = themeMode,
                onThemeModeChange = onThemeModeChange
            )
        }

        composable("${Routes.TASK_DETAIL}/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(navController, taskId)
        }

        composable(Routes.ADMIN) {
            AdminDashboardScreen(navController)
        }
    }
}
