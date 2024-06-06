package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val localDB: TaskDatabase,
) :
    ViewModel() {

    private var _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks : StateFlow<List<TaskEntity>> = _tasks

    private var _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog


    fun loadTasks(){
        viewModelScope.launch {
            _tasks.value = localDB.taskdao().getAll()
        }
    }

    fun deleteTask() {
        /*localData.delete(Constants.TITLE_KEY)
        localData.delete(Constants.CONTENT_KEY)
        _title.value = ""*/
        _showAlertDialog.value = false
    }

    fun setShowAlertDialog(value: Boolean) {
        _showAlertDialog.value = value
    }

    fun navigate(screen: String, navController: NavController) {
        navController.navigate(screen)
    }
}