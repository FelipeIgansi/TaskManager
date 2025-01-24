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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.R
import com.taskmanager.activity.CreateAccountScreen
import com.taskmanager.activity.LoginScreen
import com.taskmanager.activity.SyncDatabaseScreen
import com.taskmanager.activity.TaskAdd
import com.taskmanager.activity.TaskEdit
import com.taskmanager.activity.TaskList
import com.taskmanager.activity.WelcomeScreen
import com.taskmanager.activity.viewmodel.CreateAccountViewModel
import com.taskmanager.activity.viewmodel.LoginViewModel
import com.taskmanager.activity.viewmodel.SyncDatabaseViewModel
import com.taskmanager.activity.viewmodel.TaskAddViewModel
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.activity.viewmodel.WelcomeViewModel
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskDatabase
import com.taskmanager.data.TaskRepository
import com.taskmanager.theme.DarkGreen

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData,
    private val localdb: TaskDatabase,
    private val auth: FirebaseAuth,
    private val sessionAuth: SessionAuth,
    private val cloudDB: FirebaseFirestore,
    private val taskRepository: TaskRepository
) {
    private val taskAddViewModel by lazy { TaskAddViewModel(navController, localdb, cloudDB, auth) }
    private val taskEditViewModel by lazy { TaskEditViewModel(navController, localTaskData, localdb, cloudDB, auth) }
    private val taskListViewModel by lazy { TaskListViewModel(localdb, auth, cloudDB) }
    private val createAccountViewModel by lazy { CreateAccountViewModel(navController, auth, sessionAuth, cloudDB) }
    private val loginViewModel by lazy { LoginViewModel(navController, auth, sessionAuth) }
    private val welcomeViewModel by lazy { WelcomeViewModel(navController, sessionAuth) }
    private val syncDatabaseViewModel by lazy { SyncDatabaseViewModel(sessionAuth, navController, taskRepository) }

    @Composable
    fun buildScreen(screen: String): PaddingValues {
        val viewModel = when (screen) {
            Routes.TaskAdd.route -> taskAddViewModel
            Routes.TaskEdit.route -> taskEditViewModel
            Routes.TaskList.route -> taskListViewModel
            Routes.CreateAccount.route -> createAccountViewModel
            Routes.LoginScreen.route -> loginViewModel
            Routes.WelcomeScreen.route -> welcomeViewModel
            Routes.SyncDatabaseScreen.route -> syncDatabaseViewModel
            else -> throw IllegalArgumentException(" Não foi encontrada a tela $screen")
        }
        Scaffold(topBar = { CustomTopAppBar(screen = screen, viewModel = viewModel) }) { padding ->
            when (screen) {
                Routes.TaskAdd.route -> TaskAdd(padding, taskAddViewModel)
                Routes.TaskEdit.route -> TaskEdit(padding, taskEditViewModel)
                Routes.TaskList.route -> TaskList(padding, navController, taskListViewModel, localTaskData)
                Routes.CreateAccount.route -> CreateAccountScreen(createAccountViewModel)
                Routes.LoginScreen.route -> LoginScreen(loginViewModel)
                Routes.WelcomeScreen.route -> WelcomeScreen(welcomeViewModel)
                Routes.SyncDatabaseScreen.route -> SyncDatabaseScreen(padding,syncDatabaseViewModel)
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
                    is TaskListViewModel -> ButtonLogout(Routes.WelcomeScreen.route)
                }
            },
            navigationIcon = {
                when (viewModel) {
                    is TaskAddViewModel,
                    is TaskEditViewModel,
                    is CreateAccountViewModel,
                    is LoginViewModel -> {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                }
            })
    }

    @Composable
    fun ButtonLogout(destination: String) =
        IconButton(onClick = {
            sessionAuth.saveAuthenticationStage(destination)
            navController.navigate(destination)
        }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = null,
                Modifier.size(30.dp)
            )
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