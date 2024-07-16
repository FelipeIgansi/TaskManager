package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val navController: NavController, private val auth: FirebaseAuth) :
    ViewModel() {
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

    fun navigate(destination: String) {
        navController.navigate(destination)
    }
}