package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskEditViewModel : ViewModel() {
    private var _isSaveRequested = MutableStateFlow(false)
    val isSaveRequested: StateFlow<Boolean> = _isSaveRequested

    fun setIsSaveRequest(value: Boolean) {
        _isSaveRequested.value = value
    }
}