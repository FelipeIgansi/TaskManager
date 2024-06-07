package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val localDB: TaskDatabase,
) :
    ViewModel() {

    private var _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    private var _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog


    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = localDB.taskdao().getAll()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            localDB.taskdao().delete(task)
            _tasks.value = localDB.taskdao().getAll()
        }
        _showAlertDialog.value = false
    }

    fun setShowAlertDialog(value: Boolean) {
        _showAlertDialog.value = value
    }
}