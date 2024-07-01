package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taskmanager.base.Routes
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskAddViewModel(
    private val navController: NavController,
    private val localDB: TaskDatabase
) : ViewModel() {

    private var _title = MutableStateFlow("")
    val title: StateFlow<String?> = _title

    private var _content = MutableStateFlow("")
    val description: StateFlow<String?> = _content

    private var _isSaveRequest = MutableStateFlow(false)
    val isSaveRequest: StateFlow<Boolean> = _isSaveRequest

    fun setSaveRequest(value: Boolean) {
        _isSaveRequest.value = value
    }

    fun createTask() {
        viewModelScope.launch {
            localDB.taskdao().insertAll(TaskEntity(0, _title.value, _content.value))
        }

        navigate(Routes.TaskList.route)
    }

    private fun navigate(screen: String) {
        navController.navigate(screen)
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDescription(description: String) {
        _content.value = description
    }
}