package com.taskmanager.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.activity.TaskModel
import kotlinx.coroutines.tasks.await

class TaskRepository(
    private val cloudDB: FirebaseFirestore,
    private val localDB: TaskDatabase,
    private val auth: FirebaseAuth
) {
    suspend fun syncDataWithFirebase() {
        val uid = auth.currentUser?.uid
        if (localDB.taskdao().getAll(uid).isEmpty()) {
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
            }
        }
    }
}