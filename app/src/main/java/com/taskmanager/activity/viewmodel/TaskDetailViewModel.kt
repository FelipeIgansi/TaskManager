package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.base.Constants
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(private val localData: LocalTaskData, private val localDB: TaskDatabase) : ViewModel() {

    private var _task = MutableStateFlow(TaskEntity())
    val task : StateFlow<TaskEntity> = _task

    private var _title = MutableStateFlow("")
    val title : StateFlow<String> = _title

    private var _description = MutableStateFlow("")
    val description : StateFlow<String> = _description

    fun loadTask(){
        viewModelScope.launch {
            _task.value = localDB.taskdao().getByID(localData.getByID(Constants.TASK_KEY))
            _title.value = _task.value.title
            _description.value = _task.value.content
        }
    }
}