package com.taskmanager.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskRepository

class Navigation {
    private lateinit var navController: NavHostController
    private lateinit var localTaskData: LocalTaskData
    private lateinit var localdb: TaskDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionAuth: SessionAuth
    private lateinit var cloudDB : FirebaseFirestore
    private lateinit var taskRepository: TaskRepository

    private fun NavGraphBuilder.composableScreen(route: String) {
        composable(route) {
            CallScaffold(
                navController,
                localTaskData,
                localdb,
                auth,
                sessionAuth,
                cloudDB,
                taskRepository
            ).buildScreen(route)
        }
    }

    @Composable
    fun Create (){
        navController = rememberNavController()
        localTaskData = LocalTaskData(LocalContext.current)
        localdb = TaskDatabase.getDatabase(LocalContext.current)
        auth = FirebaseAuth.getInstance()
        sessionAuth = SessionAuth(LocalContext.current)
        cloudDB = FirebaseFirestore.getInstance()
        taskRepository = TaskRepository(
            cloudDB, localdb, auth
        )

        val startDestination = sessionAuth.getAuthentication() ?: Routes.WelcomeScreen.route

        if (startDestination.isNotEmpty()) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composableScreen(Routes.TaskList.route)
                composableScreen(Routes.TaskAdd.route)
                composableScreen(Routes.TaskEdit.route)
                composableScreen(Routes.TaskDetail.route)
                composableScreen(Routes.CreateAccount.route)
                composableScreen(Routes.LoginScreen.route)
                composableScreen(Routes.WelcomeScreen.route)
                composableScreen(Routes.SyncDatabaseScreen.route)
            }
        }
    }

}