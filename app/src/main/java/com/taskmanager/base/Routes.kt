package com.taskmanager.base

sealed class Routes(val route: String) {
    data object TaskList : Routes(Constants.ROUTES.TASKLISTROUTE)
    data object TaskEdit : Routes(Constants.ROUTES.TASKEDITROUTE)
    data object TaskAdd : Routes(Constants.ROUTES.TASKADDROUTE)
    data object TaskDetail : Routes(Constants.ROUTES.TASKDETAILROUTE)
    data object CreateAccount : Routes(Constants.ROUTES.CREATEACCOUNT)
    data object LoginScreen : Routes(Constants.ROUTES.LOGINSCREEN)
    data object WelcomeScreen : Routes(Constants.ROUTES.WELCOMESCREEN)
    data object SyncDatabaseScreen : Routes(Constants.ROUTES.SYNCDATABASESCREEN)
}