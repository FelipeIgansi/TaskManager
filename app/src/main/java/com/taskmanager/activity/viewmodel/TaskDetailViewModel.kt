package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.taskmanager.base.Constants
import com.taskmanager.data.LocalTaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskDetailViewModel(localData: LocalTaskData) : ViewModel() {
    private var _title = MutableStateFlow(localData.get(Constants.TITLE_KEY))
    val title : StateFlow<String> = _title

    private var _description = MutableStateFlow(localData.get(Constants.CONTENT_KEY))
    val description : StateFlow<String> = _description
}