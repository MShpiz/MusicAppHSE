package com.layka.musicapphse.screens.PlaylistScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.screens.AlbumScreen.PlaylistData
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor(private val repository: Repository, val tokenManager: TokenManager) :
    ViewModel() {
    val playlistData = mutableStateOf<PlaylistData>(
        PlaylistData(
            "", "", -1, "", listOf()
        )
    )

    val tracks = mutableSetOf<Int>()

    fun getPlaylist(id: Int, navController: NavController) {
        var hadException = false
        viewModelScope.launch {
            try {
                playlistData.value = repository.getPlayList(id)
            } catch (e: IllegalAccessException) {
                hadException = true
            } catch (e: Exception) {
                Log.v("PLAYLIST", e.message.toString())
            }
        }.invokeOnCompletion {
            Log.v("PLAYLIST", "got all ${playlistData.value}")
            if (hadException) {
                navController.navigate("auth_screen")
            }
        }
    }

    fun editPlaylist(name: String, description: String, navController: NavController) {
        var hadException = false
        var result = false
        viewModelScope.launch {
            try {
                result = repository.updatePlayList(name, description, playlistData.value.id)
            } catch (e: IllegalAccessException) {
                hadException = true
            } catch (e: Exception) {
                Log.v("PLAYLIST", e.message.toString())
            }
        }.invokeOnCompletion {
            Log.v("PLAYLIST", "got all ${playlistData.value}")
            if (hadException) {
                navController.navigate("auth_screen")
            }
            if (result) {
                getPlaylist(playlistData.value.id, navController)
            }
        }
    }

    fun addTrack(id: Int, checked: Boolean) {
        if (checked) {
            tracks.add(id)
        } else {
            tracks.remove(id)
        }
    }

    fun removeTracksFromPlaylist( callback: () -> Unit) {
        viewModelScope.launch {
            tracks.forEach {
                repository.deleteTrackFromPlayList(playlistData.value.id, it)
                playlistData.value = PlaylistData(
                    playlistData.value.createdAt,
                    playlistData.value.description,
                    playlistData.value.id,
                    playlistData.value.name,
                    playlistData.value.tracks.filter { track -> track.trackId != it }
                )
            }
        }.invokeOnCompletion { callback() }
    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            repository.deletePlayList(playlistId)
        }
    }
}