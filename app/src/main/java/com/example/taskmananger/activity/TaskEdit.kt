package com.example.taskmananger.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.taskmananger.TaskEditViewModel
import com.example.taskmananger.data.LocalTaskData
import com.example.taskmananger.base.Routes

@Composable
fun TaskEdit(
    padding: PaddingValues,
    navController: NavHostController,
    localTaskData: LocalTaskData,
    taskEditViewModel: TaskEditViewModel
) {
    var title by remember { mutableStateOf(localTaskData.get("title")) }
    var content by remember { mutableStateOf(localTaskData.get("content")) }
    val isSaveRequested by taskEditViewModel.isSaveRequested.collectAsState()

    LaunchedEffect(isSaveRequested) {
        if (isSaveRequested) {
            localTaskData.save("title", title)
            localTaskData.save("content", content)
            navController.navigate(Routes.TaskList.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Conteudo") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxLines = Int.MAX_VALUE
        )
    }
}
