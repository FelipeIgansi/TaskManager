package com.taskmanager.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskDatabase

class Navigation {
    private lateinit var navController: NavHostController
    private lateinit var localTaskData: LocalTaskData
    private lateinit var localdb: TaskDatabase
    private lateinit var auth: FirebaseAuth

    private fun NavGraphBuilder.composableScreen(route: String) {
        composable(route) { CallScaffold(navController, localTaskData, localdb).buildScreen(route) }
    }

    @Composable
    fun Create (){
        navController = rememberNavController()
        localTaskData = LocalTaskData(LocalContext.current)
        localdb = TaskDatabase.getDatabase(LocalContext.current)

        val startDestination = Routes.LoginScreen.route

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
            }
        }
    }

}