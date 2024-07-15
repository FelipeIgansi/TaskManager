package com.taskmanager.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.taskmanager.activity.CreateAccountScreen
import com.taskmanager.activity.LoginScreen
import com.taskmanager.activity.TaskAdd
import com.taskmanager.activity.TaskDetail
import com.taskmanager.activity.TaskEdit
import com.taskmanager.activity.TaskList
import com.taskmanager.activity.viewmodel.CreateAccountViewModel
import com.taskmanager.activity.viewmodel.LoginViewModel
import com.taskmanager.activity.viewmodel.TaskAddViewModel
import com.taskmanager.activity.viewmodel.TaskDetailViewModel
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskDatabase
import com.taskmanager.theme.DarkGreen

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData,
    localdb: TaskDatabase
) {
    private val taskAddViewModel by lazy { TaskAddViewModel(navController = navController, localDB = localdb) }
    private val taskEditViewModel by lazy { TaskEditViewModel(navController = navController, localData = localTaskData, localDB = localdb) }
    private val taskListViewModel by lazy { TaskListViewModel(localDB = localdb) }
    private val taskdetailViewModel by lazy {TaskDetailViewModel(localData = localTaskData, localDB = localdb) }
    private val createAccountViewModel by lazy { CreateAccountViewModel(navController) }
    private val loginViewModel by lazy { LoginViewModel(navController) }

    @Composable
    fun buildScreen(screen: String): PaddingValues {
        val viewModel = when (screen) {
            Routes.TaskDetail.route -> taskdetailViewModel
            Routes.TaskAdd.route -> taskAddViewModel
            Routes.TaskEdit.route -> taskEditViewModel
            Routes.TaskList.route -> taskListViewModel
            Routes.CreateAccount.route -> createAccountViewModel
            Routes.LoginScreen.route -> loginViewModel
            else -> throw IllegalArgumentException(" Não foi encontrada a tela $screen")
        }
        Scaffold(topBar = { CustomTopAppBar(screen = screen, viewModel = viewModel) }) { padding ->
            when (screen) {
                Routes.TaskDetail.route -> TaskDetail(padding, taskdetailViewModel)
                Routes.TaskAdd.route -> TaskAdd(padding, taskAddViewModel)
                Routes.TaskEdit.route -> TaskEdit(padding, taskEditViewModel)
                Routes.TaskList.route -> TaskList(
                    padding = padding,
                    navController = navController,
                    listViewModel = taskListViewModel,
                    localTaskData = localTaskData
                )


                Routes.CreateAccount.route -> CreateAccountScreen(viewModel = createAccountViewModel)
                Routes.LoginScreen.route -> LoginScreen(viewModel = loginViewModel)
            }
        }

        return PaddingValues()

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomTopAppBar(screen: String, viewModel: ViewModel) {
        val title = when (screen) {
            Routes.TaskAdd.route -> Constants.TOPAPPBARHEADER.CREATETASKTEXT
            Routes.TaskEdit.route -> Constants.TOPAPPBARHEADER.EDITTASKTEXT
            Routes.TaskList.route -> Constants.TOPAPPBARHEADER.LISTTASKTEXT
            Routes.TaskDetail.route -> Constants.TOPAPPBARHEADER.DETAILTASKTEXT
            else -> ""
        }

        CenterAlignedTopAppBar(title = { Text(text = title) },
            actions = {
                when (viewModel) {
                    is TaskAddViewModel -> ButtonSave(onSaveClick = { taskAddViewModel.setSaveRequest(true) })
                    is TaskEditViewModel -> ButtonSave(onSaveClick = { taskEditViewModel.setSaveRequest(true) })
                }
            },
            navigationIcon = {
                if (viewModel !is TaskListViewModel) {
                    IconButton(onClick = { navController.navigate(Routes.TaskList.route) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            })
    }

    @Composable
    fun ButtonSave(onSaveClick: () -> Unit) {
        IconButton(onClick = onSaveClick) {
            Icon(
                Icons.Default.Done,
                contentDescription = null,
                tint = DarkGreen,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}