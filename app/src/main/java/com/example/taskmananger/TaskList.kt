package com.example.taskmananger

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TaskList(
    padding: PaddingValues,
    navController: NavHostController,
    localTaskData: LocalTaskData
) {
    var title by remember { mutableStateOf(localTaskData.get("title")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        if (title != "") {
            Card {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp)
                ) {
                    Text(text = "$title")
                    Row {
                        IconButton(onClick = { navController.navigate(Routes.TaskEdit.route) }) {
                            Icon(Icons.Default.Edit, contentDescription = null)
                        }
                        IconButton(onClick = {
                            localTaskData.delete("title")
                            localTaskData.delete("content")
                            title = ""
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }

                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(onClick = { navController.navigate(Routes.TaskAdd.route) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}
