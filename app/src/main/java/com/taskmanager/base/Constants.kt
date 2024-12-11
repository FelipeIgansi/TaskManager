package com.taskmanager.base

class Constants {
    companion object {
        const val TITLE = "Titulo"
        const val DESCRIPTION = "Descricao"
        const val SEMREGISTROMSG = "Não há tarefas salvas"
        const val TASK_KEY = "task_key"
    }
    object AUTHENTICATION{
        const val KEYSESSION = "keySession"
        const val REGISTER = "Cadastrar"
        const val EMAIL = "E-mail"
        const val PASSWORD ="Password"
        const val LOGIN = "Login"
    }
    object WELCOMESCREEN{
        const val WELCOMETEXT = "Bem vindo(a) ao Task Manager!"
        const val PHRASE = "O melhor app de notas!"
        const val REGISTER = "Cadastrar"
        const val LOGIN = "Entrar"
    }

    object ALERTDIALOG {
        const val YES = "Sim"
        const val NO = "Nao"
        const val CONFIRMAREXCLUSAO = "Tem certeza que deseja excluir?"
    }

    object TOPAPPBARHEADER {
        const val EDITTASKTEXT = "Edição da nota"
        const val CREATETASKTEXT = "Criação de notas"
        const val LISTTASKTEXT = "Minhas notas"
    }

    object ROUTES {
        const val TASKADDROUTE = "task_add"
        const val TASKEDITROUTE = "task_edit"
        const val TASKLISTROUTE = "task_list"
        const val TASKDETAILROUTE = "task_detail"
        const val CREATEACCOUNT = "create_screen"
        const val LOGINSCREEN = "login_screen"
        const val WELCOMESCREEN = "welcome_screen"
    }

    object DATABASE {
        object FIREBASE {
            const val WEAKPASSWORDEXCEPTION = "A senha deve ter pelo menos 6 caracteres!"
            const val INVALIDCREDENTIALSEXCEPTION = "O email está incorreto!"
            const val COLLISIONEXCEPTION = "Este email já está cadastrado!"
            const val GENERICERROR = "Seu email ou senha não está correto!"
            const val MISSINGEMAILORPASSWORD ="Informe um email e senha."
        }

        const val SHAREDPREFERENCESNAME = "locataskdata"
        const val DATABASENAME = "task_database"
        const val TITLECOLUMN = "title"
        const val CONTENTCOLUMN = "content"
    }
}