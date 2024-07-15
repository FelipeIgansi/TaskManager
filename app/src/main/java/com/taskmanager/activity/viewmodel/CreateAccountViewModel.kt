package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateAccountViewModel : ViewModel() {
    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _pass = MutableStateFlow("")
    val pass: StateFlow<String> = _pass

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _pass.value = value
    }
}