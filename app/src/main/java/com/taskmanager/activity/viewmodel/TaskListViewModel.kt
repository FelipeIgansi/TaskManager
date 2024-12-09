package com.taskmanager.activity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.base.Constants
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TaskListViewModel(
    private val localDB: TaskDatabase,
    auth: FirebaseAuth,
    private val cloudDB: FirebaseFirestore
) : ViewModel() {

    private var _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    private var _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog

    private var _selectItem = MutableStateFlow(TaskEntity())
    val selectItem: StateFlow<TaskEntity> = _selectItem

    private var _isListIconSelected = MutableStateFlow(false)
    val isListIconSelected: StateFlow<Boolean> = _isListIconSelected


    fun setIsListIconSelected(value: Boolean) {
        _isListIconSelected.value = value
    }

    fun setSelectItem(value: TaskEntity) {
        _selectItem.value = value
    }

    private val uuid = auth.currentUser?.uid


    fun loadTasks() {
        viewModelScope.launch {
            setListTasks(localDB.taskdao().getAll(uuid))
        }
    }
    fun setListTasks(tasks:List<TaskEntity>){
        _tasks.value = tasks
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = cloudDB.collection(Constants.DATABASE.FIREBASE.TASKDATABASENAME)
                    .whereEqualTo("uuid", uuid)
                    .get()
                    .await()

                val taskID = querySnapshot.documents.first().id

                if (taskID.isNotEmpty()) cloudDB.collection(Constants.DATABASE.FIREBASE.TASKDATABASENAME)
                    .document(taskID)
                    .delete()

                localDB.taskdao().delete(task)
                setListTasks(localDB.taskdao().getAll(uuid))

                viewModelScope.launch(Dispatchers.Main) {
                    _showAlertDialog.value = false
                }
            } catch (e: Exception) {
                Log.e(
                    "deleteTask",
                    "deleteTask: Ocorreu um erro ao tentar deletar a task \n\n Erro: $e",
                )
            }

        }

    }

    fun setShowAlertDialog(value: Boolean) {
        _showAlertDialog.value = value
    }
}