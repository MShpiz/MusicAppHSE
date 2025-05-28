package com.layka.musicapphse.screens.AuthScreen

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layka.musicapphse.services.AuthResult
import com.layka.musicapphse.services.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService
) : ViewModel() {
    val authResult = mutableStateOf(AuthResult.NONE)
    fun register(
        email: String,
        password: String,
        username: String,
        showToast: (String) -> Unit,
        isError: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            authResult.value =
                authService.register(email = email, password = password, username = username)
        }.invokeOnCompletion {
            reactToResult(showToast, isError)
        }
    }

    fun login(
        email: String,
        password: String,
        showToast: (String) -> Unit,
        isError: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            authResult.value = authService.login(email = email, password = password)
        }.invokeOnCompletion {
            reactToResult(showToast, isError)
        }
    }

    private fun reactToResult(showToast: (String) -> Unit, isError: MutableState<Boolean>) {
        isError.value = true
        showToast(
            when (authResult.value) {
                AuthResult.OK -> "OK"
                AuthResult.NO_NETWORK -> "No network"
                AuthResult.CONFLICT -> "this username is already taken"
                AuthResult.WRONG_EMAIL_PASSWORD -> "worng email password"
                AuthResult.UNKNOWN -> "unknown error"
                AuthResult.NONE -> "unknown error"
            }
        )
    }
}