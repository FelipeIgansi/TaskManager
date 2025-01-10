package com.taskmanager.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taskmanager.activity.viewmodel.SyncDatabaseViewModel


@Composable
fun SyncDatabaseScreen(
    padding: PaddingValues,
    viewModel: SyncDatabaseViewModel
) {

    val isDataSyncronized by viewModel.isDataSyncronized.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.syncDataWithFirebase()
    }

    LaunchedEffect(isDataSyncronized) {
        viewModel.moveForward()
    }

    Box(
        Modifier
            .padding(padding)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Red,
            strokeWidth = 5.dp,
            trackColor = Color.Gray
        )
    }
}
