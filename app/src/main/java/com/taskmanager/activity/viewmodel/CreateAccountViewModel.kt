package com.taskmanager.activity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.SessionAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(
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

    private suspend fun deleteMsgErrorWithTimer() {
        delay(3000)
        if (_msgError.value.isNotEmpty()) _msgError.value = ""
    }

    private fun setMsgError(message: String) {
        viewModelScope.launch {
            _msgError.value = message
            deleteMsgErrorWithTimer()
        }
    }

    fun registerUser(email: String, passoword: String) {
        if (email.isNotEmpty() && passoword.isNotEmpty()) {
            viewModelScope.launch {
                auth.createUserWithEmailAndPassword(email, passoword)
                    .addOnFailureListener { exception ->
                        setMsgError(
                            when (exception) {
                                is FirebaseAuthWeakPasswordException -> Constants.DATABASE.FIREBASE.WEAKPASSWORDEXCEPTION
                                is FirebaseAuthInvalidCredentialsException -> Constants.DATABASE.FIREBASE.INVALIDCREDENTIALSEXCEPTION
                                is FirebaseAuthUserCollisionException -> Constants.DATABASE.FIREBASE.COLLISIONEXCEPTION
                                else -> Constants.DATABASE.FIREBASE.GENERICERROR
                            }
                        )
                        Log.i("debug Cadastro de usuario", "registerUser: $exception")

                    }
                    .addOnSuccessListener {
                        val destination = Routes.TaskList.route
                        sessionAuth.saveAuthenticationStage(destination)
                        navController.navigate(destination)
                    }
            }
        } else setMsgError(Constants.DATABASE.FIREBASE.MISSINGEMAILORPASSWORD)
    }
}