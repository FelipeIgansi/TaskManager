package com.taskmanager.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskmanager.activity.viewmodel.TaskDetailViewModel

@Composable
fun TaskDetail(
    padding: PaddingValues,
    detailViewModel: TaskDetailViewModel
) {
    val title by detailViewModel.title.collectAsState()
    val description by detailViewModel.description.collectAsState()
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Card (modifier = Modifier.fillMaxWidth()){
            Text(text = title, modifier = Modifier.padding(10.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Card (modifier = Modifier
            .weight(1f)
            .fillMaxWidth()){
            Text(text = description, modifier = Modifier.padding(10.dp))
        }

    }
}