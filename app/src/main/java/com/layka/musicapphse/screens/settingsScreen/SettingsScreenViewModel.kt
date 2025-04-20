package com.layka.musicapphse.screens.settingsScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private var initialInfo = ProfileResponse.ProfileInfo("", -1, "")

    fun getData(newEmail: MutableState<String>, newUsername: MutableState<String>) {
        viewModelScope.launch {
            initialInfo = repository.getProfile()
            newEmail.value = initialInfo.email
            newUsername.value = initialInfo.username
        }
    }

    fun updateProfile(email: String, username: String) {
        val sentInfo = ProfileResponse.ProfileInfo(
            email = if (email.isNotBlank() && email != initialInfo.email) email else initialInfo.email,
            username = if (username.isNotBlank() && username != initialInfo.username) username else initialInfo.username,
            id = initialInfo.id
        )
        viewModelScope.launch {
            initialInfo = repository.updateProfile(sentInfo)
        }
    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
            tokenManager.deleteToken()
        }.invokeOnCompletion { navController.navigate("auth_screen") }
    }
}