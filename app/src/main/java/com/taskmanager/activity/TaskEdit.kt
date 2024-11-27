package com.taskmanager.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.base.Constants

@Composable
fun TaskEdit(
    padding: PaddingValues,
    taskEditViewModel: TaskEditViewModel
) {

    LaunchedEffect(key1 = taskEditViewModel.task) { taskEditViewModel.loadTask() }

    val title by taskEditViewModel.title.collectAsState()
    val content by taskEditViewModel.content.collectAsState()
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
            placeholder = { Text(text = Constants.TITLE) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 23.sp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { taskEditViewModel.setContent(it) },
            placeholder = { Text(Constants.DESCRIPTION) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

    }
}
