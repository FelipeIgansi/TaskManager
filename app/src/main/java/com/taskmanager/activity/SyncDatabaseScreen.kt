package com.taskmanager.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.taskmanager.activity.viewmodel.SyncDatabaseViewModel
import com.taskmanager.base.SyncState


@Composable
fun SyncDatabaseScreen(
    padding: PaddingValues,
    viewModel: SyncDatabaseViewModel
) {
    val syncState by viewModel.syncState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.syncDataWithFirebase()
    }

    when(syncState){
        SyncState.SUCCSESS -> viewModel.moveForward()
        SyncState.LOADING -> LoagingProgressIndicator(padding)
        SyncState.ERROR -> ErrorMessage(padding)
    }
}

@Composable
private fun ErrorMessage(padding: PaddingValues) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(padding), contentAlignment = Alignment.Center) {
        Text("Ocorreu um erro ao sincronizar os dados, tente novamente!")
    }
}

@Composable
private fun LoagingProgressIndicator(padding: PaddingValues) {
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
