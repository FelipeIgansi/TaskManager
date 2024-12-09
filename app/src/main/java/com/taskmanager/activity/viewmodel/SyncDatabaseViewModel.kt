package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.base.Routes
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.downloadDataFromFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SyncDatabaseViewModel(
    private val auth: FirebaseAuth,
    private val localDB: TaskDatabase,
    private val cloudDB: FirebaseFirestore,
    private val navController: NavController,
    private val sessionAuth: SessionAuth
) : ViewModel() {

    private var _isDataSynchronized = MutableStateFlow(false)
    val isDataSynchronized: StateFlow<Boolean> = _isDataSynchronized

    private fun setIsDataSynchronized(value: Boolean) {
        _isDataSynchronized.value = value
    }

    fun navigateToTaskList() {
        sessionAuth.saveAuthenticationStage(Routes.TaskList.route)
        navController.navigate(Routes.TaskList.route)
    }

    suspend fun syncDataWithFirebase() {
        setIsDataSynchronized(
            downloadDataFromFirestore(
                auth = auth,
                localDB = localDB,
                cloudDB = cloudDB
            )
        )
    }
}