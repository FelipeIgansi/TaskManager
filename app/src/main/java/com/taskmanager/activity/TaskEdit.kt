package com.taskmanager.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.base.Constants

@Composable
fun TaskEdit(
    padding: PaddingValues,
    taskEditViewModel: TaskEditViewModel
) {
    val title by taskEditViewModel.title.collectAsState()
    val description by taskEditViewModel.description.collectAsState()
    val isSaveRequest by taskEditViewModel.isSaveRequest.collectAsState()

    LaunchedEffect(isSaveRequest) { if (isSaveRequest) taskEditViewModel.editarTask() }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { taskEditViewModel.setTitle(it) },
            label = { Text(text = Constants.TITLE) },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { taskEditViewModel.setDescription(it) },
            label = { Text(Constants.DESCRIPTION) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

    }
}
