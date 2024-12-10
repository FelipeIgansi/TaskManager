package com.taskmanager.activity

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import com.taskmanager.activity.viewmodel.SyncDatabaseViewModel
import com.taskmanager.theme.DarkGreen
import kotlinx.coroutines.delay

@Composable
fun SyncDatabaseScreen(padding: PaddingValues, viewModel: SyncDatabaseViewModel) {
    val isDataSynchronized by viewModel.isDataSynchronized.collectAsState()

    LaunchedEffect(Unit) { viewModel.syncDataWithFirebase() }
    LaunchedEffect(isDataSynchronized) {
        delay(1000)
        viewModel.navigateToTaskList()
    }
    val transition = rememberInfiniteTransition(label = "")
    val color = transition.animateColor(
        label = "",
        initialValue = Color.Green,
        targetValue = DarkGreen,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(300, easing = FastOutSlowInEasing)
        )
    )
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = color.value)
    }
}