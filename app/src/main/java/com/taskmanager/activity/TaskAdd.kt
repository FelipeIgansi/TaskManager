package com.taskmanager.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taskmanager.activity.viewmodel.TaskAddViewModel
import com.taskmanager.base.Constants


@Composable
fun TaskAdd(
    padding: PaddingValues,
    taskAddViewModel: TaskAddViewModel
) {

    val title by taskAddViewModel.title.collectAsState()
    val content by taskAddViewModel.content.collectAsState()
    val saveRequest by taskAddViewModel.isSaveRequest.collectAsState()

    LaunchedEffect(saveRequest) { if (saveRequest) taskAddViewModel.createTask() }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        OutlinedTextField(
            value = title ?: "",
            onValueChange = { taskAddViewModel.setTitle(it) },
            placeholder = { Text(Constants.TITLE) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
        OutlinedTextField(
            value = content ?: "",
            onValueChange = { taskAddViewModel.setContent(it) },
            placeholder = { Text(Constants.DESCRIPTION) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

    }
}
