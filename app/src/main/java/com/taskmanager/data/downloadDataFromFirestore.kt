package com.taskmanager.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.activity.TaskModel
import com.taskmanager.base.Constants
import kotlinx.coroutines.tasks.await


suspend fun downloadDataFromFirestore(
    cloudDB: FirebaseFirestore,
    auth: FirebaseAuth,
    localDB: TaskDatabase
): Boolean {
    val uid = auth.currentUser?.uid

    val querySnapshot = cloudDB.collection(Constants.DATABASE.FIREBASE.TASKDATABASENAME)
        .whereEqualTo("uuid", uid)
        .get()
        .await()

    if (!querySnapshot.isEmpty) {
        val hasLocalDataSaved = localDB.taskdao().getAll(uid).isEmpty()
        if (hasLocalDataSaved){
            querySnapshot.documents.forEach { docment ->
                val taskModel = docment.toObject(TaskModel::class.java) ?: TaskModel()

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
    return true
}