package com.example.taskmananger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun TaskDetail(
    padding: PaddingValues,
    navController: NavHostController,
    localTaskData: LocalTaskData
) {
    Column(modifier = Modifier.fillMaxSize().padding(padding)) {
        Text(text = localTaskData.get("title"))
        Text(text = localTaskData.get("content"), modifier = Modifier.weight(1f))
    }
}
