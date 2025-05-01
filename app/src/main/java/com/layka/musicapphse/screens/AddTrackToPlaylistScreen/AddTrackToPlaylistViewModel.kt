package com.layka.musicapphse.screens.AddTrackToPlaylistScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddTrackToPlaylistViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val playlistIds = mutableSetOf<Int>()
    val playlists = mutableStateListOf<ShortPlaylistData>()

    fun getPlaylists(navController: NavController) {
        var gotException = false
        viewModelScope.launch {
            Log.v("GET_PLAYLISTS", "start")
            try {
                repository.getUserPlaylists().forEach { playlists.add(it) }
            } catch (e: IllegalAccessException) {
                gotException = true
            }
        }.invokeOnCompletion {
            if (gotException) {
                navController.navigate("auth_screen")
            }
        }
    }

    fun addPlaylist(id: Int, add: Boolean) {
        if (add) {
            playlistIds.add(id)
        } else {
            playlistIds.remove(id)
        }
    }

    fun addTrackToPlaylists(trackId: Int, navController: NavController) {
        var gotException = false
        viewModelScope.launch {
            try {
                for (playlist in playlistIds) {
                    repository.addTrackToPlayList(playlist, trackId)
                }
            } catch (e: IllegalAccessException) {
                gotException = true
            }
        }.invokeOnCompletion {
            if (gotException) {
                navController.navigate("auth_screen")
            } else {
                navController.popBackStack()
            }
        }
    }
}