package com.taskmanager.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taskmanager.R
import com.taskmanager.activity.viewmodel.TaskEditViewModel

@Composable
fun TaskEdit(
    padding: PaddingValues,
    taskEditViewModel: TaskEditViewModel
) {

    LaunchedEffect(key1 = taskEditViewModel.task) { taskEditViewModel.loadTask() }

    val title by taskEditViewModel.title.collectAsState()
    val content by taskEditViewModel.content.collectAsState()
    val isSaveRequest by taskEditViewModel.isSaveRequest.collectAsState()
    val colorpallete by taskEditViewModel.colorPallette.collectAsState()
    var isCollorPalleteCLicked by remember { mutableStateOf(false) }

    LaunchedEffect(isSaveRequest) { if (isSaveRequest) taskEditViewModel.editarTask() }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { taskEditViewModel.setTitle(it) },
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
