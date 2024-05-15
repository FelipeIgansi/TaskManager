package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskEditViewModel(
    private val navController: NavController,
    private val localData: LocalTaskData
) : ViewModel() {

    private var _title = MutableStateFlow(localData.get(Constants.TITLE_KEY))
    val title: StateFlow<String> = _title

    private var _description = MutableStateFlow(localData.get(Constants.CONTENT_KEY))
    val description: StateFlow<String> = _description

    private var _isSaveRequest = MutableStateFlow(false)
    val isSaveRequest: StateFlow<Boolean> = _isSaveRequest

    fun setSaveRequest(value: Boolean) {
        _isSaveRequest.value = value
    }

    fun setTitle(title: String) {
        _title.value = title

    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun editarTask() {
        localData.save(Constants.TITLE_KEY, _title.value)
        localData.save(Constants.CONTENT_KEY, _description.value)
        navController.navigate(Routes.TaskList.route)
    }
}