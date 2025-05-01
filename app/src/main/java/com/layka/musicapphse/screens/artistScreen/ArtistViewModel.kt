package com.layka.musicapphse.screens.artistScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.network.NetworkHeaders
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: Repository,
    private val tokenManager: TokenManager
) : ViewModel() {
    val artistData = mutableStateListOf<MusicTrackData>()
    val headers = mutableStateOf(NetworkHeaders.Builder().build())

    fun getArtistData(name: String, navController: NavController) {
        var exception = false
        artistData.clear()
        viewModelScope.launch {
            try {
                val result = repository.getArtistTracks(name)
                result.forEach { artistData.add(it) }
            } catch (e: IllegalAccessException) {
                exception = true
            }
        }.invokeOnCompletion {
            if (exception) {
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