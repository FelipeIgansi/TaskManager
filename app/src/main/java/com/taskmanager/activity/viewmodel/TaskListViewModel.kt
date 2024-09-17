package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val localDB: TaskDatabase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    private var _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog

    private val uuid = auth.currentUser?.uid


    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = localDB.taskdao().getAll(uuid)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            localDB.taskdao().delete(task)
            _tasks.value = localDB.taskdao().getAll(uuid)
        }
        _showAlertDialog.value = false
    }

    fun setShowAlertDialog(value: Boolean) {
        _showAlertDialog.value = value
    }
}