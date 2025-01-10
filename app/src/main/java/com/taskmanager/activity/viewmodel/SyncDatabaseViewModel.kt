package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.activity.TaskModel
import com.taskmanager.base.Routes
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class SyncDatabaseViewModel(
    private val cloudDB: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val localDB: TaskDatabase,
    private val sessionAuth: SessionAuth,
    private val navController: NavController
) : ViewModel() {

    private var _isDataSyncronized = MutableStateFlow(false)
    val isDataSyncronized: StateFlow<Boolean> = _isDataSyncronized

    fun moveForward(){
        val destination = Routes.TaskList.route
        sessionAuth.saveAuthenticationStage(destination)
        navController.navigate(destination)
    }

    suspend fun syncDataWithFirebase() {
        val uid = auth.currentUser?.uid
        val documentSnapshots = cloudDB.collection("tasks")
            .whereEqualTo("uuid", uid)
            .get()
            .await()

        if (!documentSnapshots.isEmpty) {
            documentSnapshots.documents.forEach { doc ->
                val taskModel = doc.toObject(TaskModel::class.java) ?: TaskModel()
                localDB.taskdao().insertAll(
                    TaskEntity(
                        id = 0,
                        title = taskModel.title,
                        content = taskModel.content,
                        fkIDUser = uid
                    )
                )
            }
            _isDataSyncronized.value = true
        }
    }
}