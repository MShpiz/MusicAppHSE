package com.layka.musicapphse.screens.player

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.NetworkHeaders
import com.layka.musicapphse.services.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class PlayerModel @Inject constructor(val queueModel: QueueModel, val tokenManager: TokenManager) :
    ViewModel() {
    val headers = mutableStateOf(NetworkHeaders.Builder().build())
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