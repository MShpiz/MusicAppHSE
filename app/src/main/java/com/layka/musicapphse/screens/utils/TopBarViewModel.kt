package com.layka.musicapphse.screens.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val repository: Repository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private var creationResult = false
    private var isUnauthorised = false
    fun getAddPlaylistState(): Flow<String> {
        return tokenManager.getRepoType()
    }

    fun createPlayList(
        name: String,
        description: String,
        onCompletion: (String) -> Unit,
        navController: NavController
    ) {

        viewModelScope.launch {
            try {
                creationResult = repository.createPlayList(name, description) != null
            } catch (e: IllegalAccessException) {
                isUnauthorised = true
            }

        }.invokeOnCompletion {
            if (isUnauthorised) {
                navController.navigate("auth_screen")
            } else {
                onCompletion(
                    if (creationResult) "playlist $name created"
                    else "an error occurred try again later"
                )
            }
        }
    }

}