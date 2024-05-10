package com.example.taskmananger.activity

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmananger.Constants
import com.example.taskmananger.data.LocalTaskData

@Composable
fun TaskDetail(
    padding: PaddingValues,
    localTaskData: LocalTaskData
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        Card (modifier = Modifier.fillMaxWidth()){
            Text(text = localTaskData.get(Constants.TITLE), modifier = Modifier.padding(20.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Text(text = localTaskData.get(Constants.CONTENT), modifier = Modifier.padding(20.dp))
        }

    }
}