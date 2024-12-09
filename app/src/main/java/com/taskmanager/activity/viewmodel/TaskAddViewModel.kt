package com.taskmanager.activity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.activity.TaskModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskAddViewModel(
    private val navController: NavController,
    private val localDB: TaskDatabase,
    private val cloudDB: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private var _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private var _isSaveRequest = MutableStateFlow(false)
    val isSaveRequest: StateFlow<Boolean> = _isSaveRequest

    private var _buttonSaveIsEnabled = MutableStateFlow(false)
    val buttonSaveIsEnabled: StateFlow<Boolean> = _buttonSaveIsEnabled

    fun setButtonSaveState() {
        _buttonSaveIsEnabled.value = _title.value.isNotEmpty() || _content.value.isNotEmpty()
    }

    fun setSaveRequest(value: Boolean) {
        _isSaveRequest.value = value
    }

    fun createTask() {
        viewModelScope.launch {
            val uuid = auth.currentUser?.uid
            val taskModel = TaskModel(title = _title.value, content = _content.value, uuid = uuid)
            localDB.taskdao().insertAll(TaskEntity(0, _title.value, _content.value, uuid))

            cloudDB.collection(Constants.DATABASE.FIREBASE.TASKDATABASENAME).add(taskModel)
                .addOnSuccessListener {
                    Log.i(
                        "createTask",
                        "createTask: Cadastro da nota foi realizado com sucesso!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.i(
                        "createTask",
                        "createTask: Ocorreu o seguinte erro $e ao tentar cadastrar uma nota"
                    )
                }
        }

        navigate(Routes.TaskList.route)
    }

    private fun navigate(screen: String) {
        navController.navigate(screen)
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setContent(content: String) {
        _content.value = content
    }
}