package com.taskmanager.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taskmanager.Constants
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData

@Composable
fun TaskEdit(
    padding: PaddingValues,
    navController: NavHostController,
    localTaskData: LocalTaskData,
    taskEditViewModel: TaskEditViewModel
) {
    var title by remember { mutableStateOf(localTaskData.get(Constants.TITLE)) }
    var content by remember { mutableStateOf(localTaskData.get(Constants.CONTENT)) }
    val isSaveRequested by taskEditViewModel.isSaveRequested.collectAsState()

    LaunchedEffect(isSaveRequested) {
        if (isSaveRequested) {
            localTaskData.save(Constants.TITLE, title)
            localTaskData.save(Constants.CONTENT, content)
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
