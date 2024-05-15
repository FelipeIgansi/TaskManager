package com.taskmanager.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.taskmanager.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData
import com.taskmanager.theme.DarkGreen

@Composable
fun TaskList(
    padding: PaddingValues, navController: NavHostController, localTaskData: LocalTaskData
) {
    var title by remember { mutableStateOf(localTaskData.get(Constants.TITLE)) }
    var showAlertDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        if (showAlertDialog) {
            AlertDialog(text = { Text(text = "Deseja realmente excluir a nota?") },
                onDismissRequest = { },
                dismissButton = {
                    Button(
                        onClick = { }, colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Red, contentColor = Color.White
                        )
                    ) {
                        Text(text = "Não")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            localTaskData.delete(Constants.TITLE)
                            localTaskData.delete(Constants.CONTENT)
                            title = ""
                            showAlertDialog = false
                        }, colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = DarkGreen, contentColor = Color.White
                        )
                    ) {
                        Text(text = "Sim")
                    }
                })
        }
        if (title != "") {
            Card {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                        .clickable {
                            navController.navigate(Routes.TaskDetail.route)
                        }) {
                    Text(text = title)
                    Row {
                        IconButton(onClick = { navController.navigate(Routes.TaskEdit.route) }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                        IconButton(onClick = { showAlertDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }

                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Não há notas cadastradas")
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(onClick = { navController.navigate(Routes.TaskAdd.route) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}
