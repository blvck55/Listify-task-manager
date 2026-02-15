package com.example.listify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.listify.ListifyApp

/**
 * MainActivity is the entry point of the application.
 * It sets the content of the activity to the ListifyApp composable.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the UI content using Jetpack Compose
        setContent {
            ListifyApp()
        }
    }
}
