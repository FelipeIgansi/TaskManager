package com.example.taskmananger.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.taskmananger.TaskAddViewModel
import com.example.taskmananger.activity.TaskAdd
import com.example.taskmananger.activity.TaskDetail
import com.example.taskmananger.activity.TaskEdit
import com.example.taskmananger.activity.TaskList
import com.example.taskmananger.data.LocalTaskData

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData
) {
    private val taskAddViewModel = TaskAddViewModel()

    @Composable
    fun buildScreen(screen: String): PaddingValues {
        Scaffold(
            topBar = {
                when (screen) {
                    Routes.TaskDetail.route -> TaskDetailTopBar()
                    Routes.TaskAdd.route -> TaskAddTopBar()
//                    Routes.TaskEdit.route -> TaskEditTopBar(localTaskData)
                    Routes.TaskList.route -> TaskListTopBar()
                }
            }
        ) { padding ->
            when (screen) {
                Routes.TaskDetail.route -> TaskDetail(padding, navController, localTaskData)
                Routes.TaskAdd.route -> TaskAdd(
                    padding,
                    localTaskData,
                    navController,
                    taskAddViewModel
                )

                Routes.TaskEdit.route -> TaskEdit(padding, navController, localTaskData)
                Routes.TaskList.route -> TaskList(padding, navController, localTaskData)
            }
        }

        return PaddingValues()

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskListTopBar() {
        CenterAlignedTopAppBar(title = { Text(text = "Task Manager") })
    }

    @Composable
    fun TaskEditTopBar(localTaskData: LocalTaskData) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskAddTopBar() {

        CenterAlignedTopAppBar(
            title = { Text(text = "Criar de nota") },
            actions = {
                IconButton(onClick = { taskAddViewModel.setIsSaveRequest(true) }) {
                    Icon(Icons.Default.Done, contentDescription = null, tint = Color.Green)
                }
            },
            navigationIcon = {
                IconButton(onClick = { taskAddViewModel.setIsSaveRequest(false) }) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskDetailTopBar() {
        TopAppBar(title = { Text(text = "Task mananger") })
    }

}