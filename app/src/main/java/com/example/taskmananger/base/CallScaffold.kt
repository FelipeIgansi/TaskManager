package com.example.taskmananger.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.taskmananger.activity.TaskAdd
import com.example.taskmananger.activity.TaskDetail
import com.example.taskmananger.activity.TaskEdit
import com.example.taskmananger.activity.TaskList
import com.example.taskmananger.data.LocalTaskData

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData
) {
    @Composable
    fun buildScreen(screen: String): PaddingValues {
        Scaffold(
            topBar = {
                /*when (screen) {
                    Routes.TaskDetail.route -> TaskDetailTopBar()
                    Routes.TaskAdd.route -> TaskAddTopBar(localTaskData)
                    Routes.TaskEdit.route -> TaskEditTopBar(localTaskData)
                    Routes.TaskList.route -> TaskListTopBar(localTaskData)
                }*/
            }
        ) { padding ->
            when (screen) {
                Routes.TaskDetail.route -> TaskDetail(padding, navController, localTaskData)
                Routes.TaskAdd.route -> TaskAdd(padding, navController, localTaskData)
                Routes.TaskEdit.route -> TaskEdit(padding, navController, localTaskData)
                Routes.TaskList.route -> TaskList(padding, navController, localTaskData)
            }
        }

        return PaddingValues()

    }

    private @Composable
    fun TaskListTopBar(localTaskData: LocalTaskData) {
        TODO("Not yet implemented")
    }

    private @Composable
    fun TaskEditTopBar(localTaskData: LocalTaskData) {
        TODO("Not yet implemented")
    }

    private @Composable
    fun TaskAddTopBar(localTaskData: LocalTaskData) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private
    @Composable
    fun TaskDetailTopBar() {
        TopAppBar(title = { Text(text = "Task mananger") })
    }

}
