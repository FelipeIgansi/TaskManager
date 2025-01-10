package com.taskmanager.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.SessionAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navController: NavController,
    private val auth: FirebaseAuth,
    private val sessionAuth: SessionAuth
) : ViewModel() {
    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _pass = MutableStateFlow("")
    val pass: StateFlow<String> = _pass

    private var _msgError = MutableStateFlow("")
    val msgError: StateFlow<String> = _msgError

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _pass.value = value
    }

    private suspend fun deleteMsgErroWithTimer() {
        delay(3000)
        if (_msgError.value.isNotEmpty()) _msgError.value = ""
    }

    private fun setMsgError(message: String) {
        viewModelScope.launch {
            _msgError.value = message
            deleteMsgErroWithTimer()
        }
    }

    fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener { exception ->
                        setMsgError(when (exception) {
                                is FirebaseAuthInvalidCredentialsException -> Constants.DATABASE.FIREBASE.INVALIDCREDENTIALSEXCEPTION
                                else -> Constants.DATABASE.FIREBASE.GENERICERROR
                            })
                    }
                    .addOnSuccessListener {
                        val destination = Routes.SyncDatabaseScreen.route
                        sessionAuth.saveAuthenticationStage(destination)
                        navController.navigate(destination)
                    }
            }
        } else setMsgError(Constants.DATABASE.FIREBASE.MISSINGEMAILORPASSWORD)
    }
}