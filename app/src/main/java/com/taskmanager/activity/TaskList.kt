package com.taskmanager.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskEntity

@Composable
fun TaskList(
    padding: PaddingValues,
    navController: NavHostController,
    listViewModel: TaskListViewModel,
    localTaskData: LocalTaskData
) {

    LaunchedEffect(key1 = listViewModel.tasks) { listViewModel.loadTasks() }
    val tasks by listViewModel.tasks.collectAsState()
    val showAlertDialog by listViewModel.showAlertDialog.collectAsState()
    var selectItem by remember { mutableStateOf(TaskEntity()) }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        if (showAlertDialog) {
            AlertDialog(onDismissRequest = { listViewModel.setShowAlertDialog(false) },
                confirmButton = {
                    Button(onClick = {
                        listViewModel.deleteTask(selectItem)
                    }) {
                        Text(text = Constants.YES)
                    }
                },
                dismissButton = {
                    Button(onClick = { listViewModel.setShowAlertDialog(false) }) {
                        Text(text = Constants.NO)
                    }
                },
                text = { Text(text = Constants.CONFIRMAREXCLUSAO) }
            )
        }
        if (tasks.isNotEmpty()) {
            LazyColumn {
                tasks.forEach { task ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable { navController.navigate(Routes.TaskDetail.route) }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = task.title,
                                    modifier = Modifier.padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    )
                                )
                                Box(modifier = Modifier) {
                                    Row {
                                        IconButton(onClick = {
                                            selectItem = task
                                            listViewModel.setShowAlertDialog(true)
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = null)
                                        }
                                        IconButton(onClick = {
                                            localTaskData.saveID(Constants.TASK_KEY, task.id)
                                            navController.navigate(Routes.TaskEdit.route)
                                        }) {
                                            Icon(Icons.Default.Edit, contentDescription = null)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = Constants.SEMREGISTROMSG)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(onClick = { navController.navigate(Routes.TaskAdd.route) }) {
            Text(text = "+")
        }
    }

}