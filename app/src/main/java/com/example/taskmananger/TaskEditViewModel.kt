package com.example.taskmananger

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskEditViewModel {
    private var _isSaveRequested = MutableStateFlow(false)
    val isSaveRequested: StateFlow<Boolean> = _isSaveRequested

    fun setIsSaveRequest(value: Boolean){
        _isSaveRequested.value = value
    }
}