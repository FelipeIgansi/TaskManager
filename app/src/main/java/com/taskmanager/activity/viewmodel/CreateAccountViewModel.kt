package com.taskmanager.activity.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.taskmanager.activity.UserModel
import com.taskmanager.base.Constants
import com.taskmanager.base.Routes
import com.taskmanager.data.SessionAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateAccountViewModel(
    private val navController: NavController,
    private val auth: FirebaseAuth,
    private val sessionAuth: SessionAuth,
    private val cloudDB: FirebaseFirestore
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

    fun saveUser(email: String, passoword: String) {
        val name = email.substring(0, email.indexOf("@"))
        val mapUser = UserModel(
                name = name,
                email = email
            )
        if (email.isNotEmpty() && passoword.isNotEmpty()) {
            viewModelScope.launch {
                auth.createUserWithEmailAndPassword(email, passoword)
                    .addOnCompleteListener {
                        saveOnCloudDB(mapUser)
                    }
                    .addOnFailureListener { exception ->
                        setMsgError(verifyErrorMessage(exception))
                        Log.i("debug Cadastro de usuario", "registerUser: $exception")

                    }
                    .addOnSuccessListener {
                        val destination = Routes.SyncDatabaseScreen.route
                        sessionAuth.saveAuthenticationStage(destination)
                        navController.navigate(destination)
                    }
            }
        } else setMsgError(Constants.DATABASE.FIREBASE.EXCEPTIONS.MISSINGEMAILORPASSWORD)
    }

    private fun verifyErrorMessage(exception: Exception) = when (exception) {
        is FirebaseAuthWeakPasswordException -> Constants.DATABASE.FIREBASE.EXCEPTIONS.WEAKPASSWORDEXCEPTION
        is FirebaseAuthInvalidCredentialsException -> Constants.DATABASE.FIREBASE.EXCEPTIONS.INVALIDCREDENTIALSEXCEPTION
        is FirebaseAuthUserCollisionException -> Constants.DATABASE.FIREBASE.EXCEPTIONS.COLLISIONEXCEPTION
        else -> Constants.DATABASE.FIREBASE.EXCEPTIONS.GENERICERROR
    }

    private fun saveOnCloudDB(mapUser: UserModel) {
        cloudDB.collection("users").add(mapUser)
            .addOnFailureListener { e ->
                Log.i(
                    "registerUser",
                    "registerUser: Ocorreu o erro: $e ao tentar realizar um cadastro de usuário"
                )
            }
            .addOnSuccessListener {
                Log.i("registerUser", "registerUser: O usuário foi salvo com sucesso!")
            }
    }
}