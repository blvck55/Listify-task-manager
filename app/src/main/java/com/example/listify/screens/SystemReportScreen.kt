package com.example.listify.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.listify.components.BackgroundPage
import com.example.listify.data.TaskStore
import com.example.listify.utils.ScreenType
import com.example.listify.utils.getScreenType

@Composable
fun SystemReportScreen(nav: NavHostController) {

    val screenType = getScreenType()

    BackgroundPage(isLanding = false) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(
                    if (screenType == ScreenType.TABLET) 0.75f else 1f
                )
            ) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "SYSTEM USAGE REPORT",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(Modifier.height(12.dp))

                        Text("Total Active Tasks: ${TaskStore.tasks.size}")
                        Spacer(Modifier.height(6.dp))

                        Text("Total Completed Tasks: ${TaskStore.history.size}")
                        Spacer(Modifier.height(6.dp))

                        val total = TaskStore.tasks.size + TaskStore.history.size
                        val completionRate =
                            if (total == 0) 0 else (TaskStore.history.size * 100) / total

                        Text("Completion Rate: $completionRate %")

                        Spacer(Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = completionRate / 100f,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { nav.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("BACK TO ADMIN DASHBOARD")
                }
            }
        }
    }
}
