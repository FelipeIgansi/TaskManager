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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.taskmanager.R
import com.taskmanager.base.Constants
import com.taskmanager.activity.viewmodel.TaskAddViewModel


@Composable
fun TaskAdd(
    padding: PaddingValues,
    taskAddViewModel: TaskAddViewModel
) {

    val title by taskAddViewModel.title.collectAsState()
    val content by taskAddViewModel.content.collectAsState()
    val saveRequest by taskAddViewModel.isSaveRequest.collectAsState()
    val colorpallete by taskAddViewModel.colorPallette.collectAsState()

    var isCollorPalleteCLicked by remember { mutableStateOf(false) }

    LaunchedEffect(saveRequest) { if (saveRequest) taskAddViewModel.createTask() }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        OutlinedTextField(
            value = title?:"",
            onValueChange = {taskAddViewModel.setTitle(it)},
            placeholder = { Text(Constants.TITLE) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
        )
        OutlinedTextField(
            value = content?:"",
            onValueChange = { taskAddViewModel.setContent(it)},
            placeholder = { Text(Constants.DESCRIPTION) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
        )

    }
}
