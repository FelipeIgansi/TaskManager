package com.taskmanager.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taskmanager.activity.TaskAdd
import com.taskmanager.activity.TaskDetail
import com.taskmanager.activity.TaskEdit
import com.taskmanager.activity.TaskList
import com.taskmanager.activity.viewmodel.TaskAddViewModel
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.data.LocalTaskData

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData
) {
    private val taskAddViewModel = TaskAddViewModel()
    private val taskEditViewModel = TaskEditViewModel()

    @Composable
    fun buildScreen(screen: String): PaddingValues {
        Scaffold(
            topBar = {
                when (screen) {
                    Routes.TaskDetail.route -> TaskDetailTopBar()
                    Routes.TaskAdd.route -> TaskAddTopBar()
                    Routes.TaskEdit.route -> TaskEditTopBar()
                    Routes.TaskList.route -> TaskListTopBar()
                }
            }
        ) { padding ->
            when (screen) {
                Routes.TaskDetail.route -> TaskDetail(padding, localTaskData)
                Routes.TaskAdd.route -> TaskAdd(
                    padding,
                    localTaskData,
                    navController,
                    taskAddViewModel
                )

                Routes.TaskEdit.route -> TaskEdit(
                    padding,
                    navController,
                    localTaskData,
                    taskEditViewModel
                )

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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskEditTopBar() {
        CenterAlignedTopAppBar(
            title = { Text(text = "Editar nota") },
            actions = {
                IconButton(onClick = { taskEditViewModel.setIsSaveRequest(true) }) {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.TaskList.route) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskAddTopBar() {

        CenterAlignedTopAppBar(
            title = { Text(text = "Criar de nota") },
            actions = {
                IconButton(onClick = { taskAddViewModel.setIsSaveRequest(true) }) {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.TaskList.route) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TaskDetailTopBar() {
        CenterAlignedTopAppBar(
            title = { Text(text = "Task manager") },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Routes.TaskList.route) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }

}