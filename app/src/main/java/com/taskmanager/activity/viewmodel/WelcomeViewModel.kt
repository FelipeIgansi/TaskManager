package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.taskmanager.data.SessionAuth

class WelcomeViewModel(
    private val navController: NavController,
    private val sessionAuth: SessionAuth
) : ViewModel() {
    fun navigate(destination: String) {
        sessionAuth.saveAuthenticationStage(destination)
        navController.navigate(destination)
    }
}