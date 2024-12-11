package com.taskmanager.base

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.R
import com.taskmanager.activity.CreateAccountScreen
import com.taskmanager.activity.LoginScreen
import com.taskmanager.activity.TaskAdd
import com.taskmanager.activity.TaskEdit
import com.taskmanager.activity.TaskList
import com.taskmanager.activity.WelcomeScreen
import com.taskmanager.activity.viewmodel.CreateAccountViewModel
import com.taskmanager.activity.viewmodel.LoginViewModel
import com.taskmanager.activity.viewmodel.TaskAddViewModel
import com.taskmanager.activity.viewmodel.TaskEditViewModel
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.activity.viewmodel.WelcomeViewModel
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.SessionAuth
import com.taskmanager.data.TaskDatabase
import kotlinx.coroutines.delay

class CallScaffold(
    private val navController: NavHostController,
    private val localTaskData: LocalTaskData,
    private val localdb: TaskDatabase,
    private val auth: FirebaseAuth,
    private val sessionAuth: SessionAuth,
    private val cloudDB: FirebaseFirestore
) {
    private val taskAddViewModel by lazy {
        TaskAddViewModel(
            navController,
            localdb,
            cloudDB,
            auth
        )
    }
    private val taskEditViewModel by lazy {
        TaskEditViewModel(
            navController,
            localTaskData,
            localdb,
            cloudDB,
            auth
        )
    }
    private val taskListViewModel by lazy {
        TaskListViewModel(
            localdb,
            auth,
            cloudDB
        )
    }
    private val createAccountViewModel by lazy {
        CreateAccountViewModel(
            navController,
            auth,
            sessionAuth,
            cloudDB
        )
    }
    private val loginViewModel by lazy {
        LoginViewModel(
            navController,
            auth,
            sessionAuth
        )
    }
    private val welcomeViewModel by lazy {
        WelcomeViewModel(
            navController,
            sessionAuth
        )
    }

    @Composable
    fun buildScreen(screen: String): PaddingValues {
        val viewModel = when (screen) {
            Routes.TaskAdd.route -> taskAddViewModel
            Routes.TaskEdit.route -> taskEditViewModel
            Routes.TaskList.route -> taskListViewModel
            Routes.CreateAccount.route -> createAccountViewModel
            Routes.LoginScreen.route -> loginViewModel
            Routes.WelcomeScreen.route -> welcomeViewModel
            else -> throw IllegalArgumentException(" Não foi encontrada a tela $screen")
        }
        Scaffold(
            topBar = {
                when(screen){
                    Routes.TaskAdd.route,
                    Routes.TaskEdit.route,
                    Routes.TaskList.route -> {
                        CustomTopAppBar(screen = screen, viewModel = viewModel)
                    }
                }
            }
        )
        { padding ->
            when (screen) {
                Routes.TaskAdd.route -> TaskAdd(
                    padding,
                    taskAddViewModel
                )

                Routes.TaskEdit.route -> TaskEdit(
                    padding,
                    taskEditViewModel
                )

                Routes.TaskList.route -> TaskList(
                    padding,
                    navController,
                    taskListViewModel,
                    localTaskData
                )

                Routes.CreateAccount.route -> CreateAccountScreen(
                    createAccountViewModel
                )

                Routes.LoginScreen.route -> LoginScreen(
                    loginViewModel
                )

                Routes.WelcomeScreen.route -> WelcomeScreen(
                    welcomeViewModel
                )
            }
        }
        return PaddingValues()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CustomTopAppBar(screen: String, viewModel: ViewModel) {
        var displayedText by remember { mutableStateOf("") }
        val title = when (screen) {
            Routes.TaskAdd.route -> Constants.TOPAPPBARHEADER.CREATETASKTEXT
            Routes.TaskEdit.route -> Constants.TOPAPPBARHEADER.EDITTASKTEXT
            Routes.TaskList.route -> Constants.TOPAPPBARHEADER.LISTTASKTEXT
            else -> ""
        }
        LaunchedEffect(Unit) {
            title.forEach { c ->
                displayedText += c.toString()
                delay(120L)
            }
        }

        CenterAlignedTopAppBar(
            title = { Text(text = displayedText) },
            actions = {
                when (viewModel) {
                    is TaskAddViewModel -> ButtonSave(
                        onSaveClick = { taskAddViewModel.setSaveRequest(true) },
                        isEnabled = taskAddViewModel.buttonSaveIsEnabled.collectAsState().value
                    )

                    is TaskEditViewModel -> ButtonSave(
                        onSaveClick = { taskEditViewModel.setSaveRequest(true) }
                    )

                    is TaskListViewModel -> ButtonLogout(Routes.WelcomeScreen.route)
                }
            },
            navigationIcon = {
                val isListIconCliecked by taskListViewModel.isListIconSelected.collectAsState()
                when (viewModel) {
                    is TaskAddViewModel,
                    is TaskEditViewModel,
                    is CreateAccountViewModel,
                    is LoginViewModel -> {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                    is TaskListViewModel -> {
                        ListOptions(
                            onListIconCLicked = { newValue ->
                                taskListViewModel.setIsListIconSelected(newValue)
                            },
                            isListSelected = isListIconCliecked,
                        )
                    }
                }
            }
        )
    }

    @Composable
    private fun ListOptions(onListIconCLicked: (Boolean) -> Unit, isListSelected: Boolean) {
        val transition = rememberInfiniteTransition(label = "")
        val colorItemSelected = transition.animateColor(
            initialValue = Color.Red,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                repeatMode = RepeatMode.Reverse,
                animation = tween(1000)
            ),
            label = ""
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            IconButton(onClick = { onListIconCLicked(false) }, modifier = Modifier.size(25.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.grid),
                    contentDescription = null,
                    tint = if (!isListSelected) colorItemSelected.value else Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = { onListIconCLicked(true) }, modifier = Modifier.size(25.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = null,
                    tint = if (isListSelected) colorItemSelected.value else Color.Gray,
                    modifier = Modifier.size(22.dp)
                )
            }

        }
    }

    @Composable
    private fun ButtonLogout(destination: String) =
        IconButton(onClick = {
            sessionAuth.saveAuthenticationStage(destination)
            navController.navigate(destination)
        }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = null,
                Modifier.size(25.dp)
            )
        }


    @Composable
    private fun ButtonSave(onSaveClick: () -> Unit, isEnabled: Boolean = true) {
        val transition = rememberInfiniteTransition(label = "")
        val colorAnitated = transition.animateColor(
            initialValue = Color.Green,
            targetValue = Color.Blue,
            animationSpec = infiniteRepeatable(
                repeatMode = RepeatMode.Reverse,
                animation = tween(600)
            ),
            label = ""
        )

        IconButton(
            onClick = onSaveClick,
            enabled = isEnabled
        ) {
            Icon(
                Icons.Default.Done,
                contentDescription = null,
                tint = if (isEnabled) colorAnitated.value else Color.Gray,
                modifier = Modifier.size(30.dp),
            )
        }
    }
}