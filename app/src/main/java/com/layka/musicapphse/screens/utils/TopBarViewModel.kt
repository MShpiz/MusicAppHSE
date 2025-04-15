package com.layka.musicapphse.screens.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var creationResult = false
    private var isUnauthorised = false
    fun createPlayList(
        name: String,
        description: String,
        onCompletion: (String) -> Unit,
        navController: NavController
    ) {

        viewModelScope.launch {
            try {
                creationResult = repository.createPlayList(name, description)
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