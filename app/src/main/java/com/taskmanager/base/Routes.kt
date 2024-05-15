package com.taskmanager.base

sealed class Routes (val route: String) {
    data object TaskList : Routes("task_list")
    data object TaskEdit : Routes("task_edit")
    data object TaskAdd : Routes("task_add")
    data object TaskDetail : Routes("task_detail")
}