package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class WelcomeViewModel(private  val navController: NavController) : ViewModel() {
    fun navigate(destination: String){
        navController.navigate(destination)
    }
}