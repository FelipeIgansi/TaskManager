package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskEditViewModel(
    private val navController: NavController,
    private val localData: LocalTaskData,
    private val localDB: TaskDatabase
) : ViewModel() {

    private var _task = MutableStateFlow(TaskEntity())
    val task : StateFlow<TaskEntity> = _task

    private var _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private var _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private var _isSaveRequest = MutableStateFlow(false)
    val isSaveRequest: StateFlow<Boolean> = _isSaveRequest

    fun loadTask(){
        viewModelScope.launch {
            _task.value = localDB.taskdao().getByID(localData.getByID(Constants.TASK_KEY))
            setTitle(_task.value.title)
            setContent(_task.value.content)
        }
    }

    fun setSaveRequest(value: Boolean) {
        _isSaveRequest.value = value
    }

    fun setTitle(title: String) {
        _title.value = title

    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun editarTask() {
        viewModelScope.launch {
            localDB.taskdao().update(TaskEntity(_task.value.id, _title.value, _content.value))
        }
        navController.navigate(Routes.TaskList.route)
    }
}