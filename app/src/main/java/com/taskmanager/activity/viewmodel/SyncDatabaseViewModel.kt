package com.taskmanager.activity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taskmanager.base.Routes
import com.taskmanager.base.SyncState
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyncDatabaseViewModel(
    private val sessionAuth: SessionAuth,
    private val navController: NavController,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private var _syncState = MutableStateFlow(SyncState.LOADING)
    val syncState: StateFlow<SyncState> = _syncState

    fun moveForward() {
        val destination = Routes.TaskList.route
        sessionAuth.saveAuthenticationStage(destination)
        navController.navigate(destination)
    }

    fun syncDataWithFirebase() {
        _syncState.value = SyncState.LOADING
        try {
            viewModelScope.launch {
                taskRepository.syncDataWithFirebase()
            }
            _syncState.value = SyncState.SUCCESS
        } catch (e: Exception) {
            Log.w("syncDataWithFirebase", "Ocorreu o seguinte erro: ${e.message}")
            _syncState.value = SyncState.ERROR
        }
    }
}