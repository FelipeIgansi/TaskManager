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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taskmanager.base.Constants
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.base.Routes

@Composable
fun TaskList(
    padding: PaddingValues,
    navController: NavHostController,
    listViewModel: TaskListViewModel
) {
    val title by listViewModel.title.collectAsState()
    val showAlertDialog by listViewModel.showAlertDialog.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        if (showAlertDialog) {
            AlertDialog(onDismissRequest = { listViewModel.setShowAlertDialog(false) },
                confirmButton = {
                    Button(onClick = {
                        listViewModel.deleteTask()
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
        if (title != "") {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        listViewModel.navigate(
                            Routes.TaskDetail.route,
                            navController
                        )
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title ?: "",
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                    )
                    Box(modifier = Modifier) {
                        Row {
                            IconButton(onClick = { listViewModel.setShowAlertDialog(true) }) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            }
                            IconButton(onClick = {
                                listViewModel.navigate(Routes.TaskEdit.route, navController)
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = null)
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
        FloatingActionButton(onClick = {
            listViewModel.navigate(
                Routes.TaskAdd.route,
                navController
            )
        }) {
            Text(text = "+")
        }
    }

}