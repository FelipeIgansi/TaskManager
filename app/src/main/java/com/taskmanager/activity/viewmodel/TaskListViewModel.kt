package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.taskmanager.base.Constants
import com.taskmanager.data.LocalTaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskListViewModel(private val localData: LocalTaskData) : ViewModel() {

    private var _title = MutableStateFlow(localData.get(Constants.TITLE_KEY))
    val title: StateFlow<String?> = _title

    private var _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog

    fun deleteTask() {
        localData.delete(Constants.TITLE_KEY)
        localData.delete(Constants.CONTENT_KEY)
        _title.value = ""
        _showAlertDialog.value = false
    }

    fun setShowAlertDialor(value: Boolean) {
        _showAlertDialog.value = value
    }

    fun navigate(screen: String, navController: NavController) {
        navController.navigate(screen)
    }
}