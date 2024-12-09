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
import com.taskmanager.activity.viewmodel.SyncDatabaseViewModel

@Composable
fun SyncDatabaseScreen(padding: PaddingValues, viewModel: SyncDatabaseViewModel) {
    val isDataSynchronized by viewModel.isDataSynchronized.collectAsState()

    LaunchedEffect(Unit) { viewModel.syncDataWithFirebase() }
    LaunchedEffect(isDataSynchronized) { viewModel.navigateToTaskList() }

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}