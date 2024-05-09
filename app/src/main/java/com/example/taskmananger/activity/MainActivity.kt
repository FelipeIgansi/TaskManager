package com.example.taskmananger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taskmananger.base.Navigation
import com.example.taskmananger.ui.theme.TaskManangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManangerTheme {
                Navigation().Create()
            }
        }
    }
}