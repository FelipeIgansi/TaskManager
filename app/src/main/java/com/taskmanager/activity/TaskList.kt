package com.taskmanager.activity

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.taskmanager.activity.viewmodel.TaskListViewModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.LocalTaskData
import com.taskmanager.data.TaskEntity

@OptIn(ExperimentalFoundationApi::class)
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
    val selectItem by listViewModel.selectItem.collectAsState()
    val isListIconSelected by listViewModel.isListIconSelected.collectAsState()


    val transition = rememberInfiniteTransition(label = "")
    val borderProgress by transition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        ),
        label = ""
    )

    val fabBorderAnimeteColor by transition.animateColor(
        initialValue = Color.Blue,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
        ),
        label = ""
    )

    val animateSizeButton by transition.animateValue(
        initialValue = 60.dp,
        targetValue = 65.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(800, easing = LinearEasing)
        ),
        label = ""
    )

    listViewModel.setListTasks(tasks.sortedBy { it.title })
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
                        Text(text = Constants.ALERTDIALOG.YES)
                    }
                },
                dismissButton = {
                    Button(onClick = { listViewModel.setShowAlertDialog(false) }) {
                        Text(text = Constants.ALERTDIALOG.NO)
                    }
                },
                text = { Text(text = Constants.ALERTDIALOG.CONFIRMAREXCLUSAO) }
            )
        }
        if (tasks.isNotEmpty()) {
            if (isListIconSelected) {
                LazyColumn {
                    tasks.forEach { task ->
                        item {
                            CardComponent(
                                borderProgress,
                                localTaskData,
                                task,
                                navController,
                                listViewModel
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    tasks.forEach { task ->
                        item {
                            CardComponent(
                                borderProgress,
                                localTaskData,
                                task,
                                navController,
                                listViewModel
                            )
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
        FloatingActionButton(
            onClick = { navController.navigate(Routes.TaskAdd.route) },
            containerColor = fabBorderAnimeteColor,
            shape = RoundedCornerShape(10.dp),
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            modifier = Modifier.size(animateSizeButton)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }

}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun CardComponent(
    borderProgress: Float,
    localTaskData: LocalTaskData,
    task: TaskEntity,
    navController: NavHostController,
    listViewModel: TaskListViewModel
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

            .drawBehind {
                // Desenhar o gradiente animado como borda
                val borderThickness = 5.dp.toPx()

                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green,
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(
                            size.width * borderProgress,
                            size.height
                        )
                    ),
                    size = Size(size.width, size.height),
                    style = Stroke(
                        width = borderThickness,
                        cap = StrokeCap.Round
                    ),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .combinedClickable(
                onClick = {
                    localTaskData.saveID(Constants.TASK_KEY, task.id)
                    navController.navigate(Routes.TaskEdit.route)
                },
                onLongClick = {
                    listViewModel.setSelectItem(task)
                    listViewModel.setShowAlertDialog(true)
                })
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.title,
                fontSize = 23.sp,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
            )
            Text(
                text = task.content,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
            )

        }
    }
}