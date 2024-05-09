package com.example.taskmananger.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmananger.data.LocalTaskData

class Navigation {
    private lateinit var navController: NavHostController
    private lateinit var localTaskData: LocalTaskData

    private fun NavGraphBuilder.composableScreen(route: String) {
        composable(route) { CallScaffold(navController, localTaskData).buildScreen(route) }
    }

    @Composable
    fun Create (){
        navController = rememberNavController()
        localTaskData = LocalTaskData(LocalContext.current)

        val startDestination = Routes.TaskList.route

        if (!startDestination.isNullOrEmpty()) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composableScreen(Routes.TaskList.route)
                composableScreen(Routes.TaskAdd.route)
                composableScreen(Routes.TaskEdit.route)
                composableScreen(Routes.TaskDetail.route)
            }
        }
    }

}