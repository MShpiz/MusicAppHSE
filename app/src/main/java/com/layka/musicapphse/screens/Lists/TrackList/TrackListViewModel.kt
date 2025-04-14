package com.layka.musicapphse.screens.Lists.TrackList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.network.NetworkHeaders
import com.layka.musicapphse.screens.player.QueueModel
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class TrackListViewModel @Inject constructor(
    val queueModel: QueueModel,
    val repository: Repository,
    val tokenManager: TokenManager
) : ViewModel() {

    val headers = mutableStateOf(NetworkHeaders.Builder().build())
    fun deleteTrack(id: Int, navController: NavController) {
        var isAuth = true
        viewModelScope.launch {
            try {
                repository.deleteTrack(id)
            } catch (e: IllegalAccessException) {
                isAuth = false
            }
        }.invokeOnCompletion {
            if (!isAuth) {
                navController.navigate("auth_screen")
            }
        }
    }

    fun getHeaders() {
        var token: String = ""
        viewModelScope.launch {
            token = tokenManager.getToken().first()
        }.invokeOnCompletion {
            headers.value = NetworkHeaders.Builder()
                .set("Authorization", "Bearer $token")
                .build()
        }

    }
}