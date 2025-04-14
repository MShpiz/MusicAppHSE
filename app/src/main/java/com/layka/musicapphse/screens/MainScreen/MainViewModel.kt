package com.layka.musicapphse.screens.MainScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.math.min

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val trackData = mutableStateListOf<MusicTrackData>()
    val playListData = mutableStateListOf<ShortPlaylistData>()
    val artistData = mutableStateListOf<ArtistData>()

    fun getAllData(navController: NavController) {
        getRecentTracks(navController)
        getPlaylists(navController)
    }

    fun getRecentTracks(navController: NavController) {
        trackData.clear()
        var exception: IllegalAccessException? = null
        viewModelScope.launch {
            try {
                Log.v("RECENT_TRACKS", "start getting")
                val result = repository.getAllTracks()
                getAllArtists(result)
                Log.v("RECENT_TRACKS", result.toString())
                result.subList(0, min(5, result.size)).forEach { trackData.add(it) }
            } catch (e: IllegalAccessException) {
                exception = e
                Log.v("RECENT_TRACKS", e.message.toString())
            } catch (e: Exception) {
                Log.v("RECENT_TRACKS", e.message.toString())
            }
        }.invokeOnCompletion {
            Log.v("RECENT_TRACKS", "got all")
            if (exception != null) {
                Log.v("RECENT_TRACKS", exception!!.message.toString())
                navController.navigate("auth_screen")
            }
        }
    }

    private fun getAllArtists(tracks: List<MusicTrackData>) {
        artistData.clear()
        tracks.map { it.artists }.toSet().forEach { artistData.add(ArtistData(it)) }
    }

    private fun getPlaylists(navController: NavController) {
        playListData.clear()
        var exception = false
        viewModelScope.launch {
            try {
                Log.v("RECENT_TRACKS", "start getting")
                val result = repository.getUserPlaylists()
                Log.v("RECENT_TRACKS", result.toString())
                result.subList(0, min(5, result.size)).forEach { playListData.add(it) }
            } catch (e: IllegalAccessException) {
                exception = true
                Log.v("RECENT_TRACKS", e.message.toString())
            } catch (e: Exception) {
                Log.v("RECENT_TRACKS", e.message.toString())
            }
        }.invokeOnCompletion {
            Log.v("RECENT_TRACKS", "got all")
            if (exception) {
                Log.v("RECENT_TRACKS", "navigating")
                navController.navigate("auth_screen")
            }
        }
    }

    fun getRecentArtists(navController: NavController) {
        trackData.clear()
        viewModelScope.launch {
            try {
                val result = repository.getRecentArtist()
                // result.subList(0, min(5, result.size)).forEach { artistData.add(it) }
            } catch (e: IllegalAccessException) {
                // navController.navigate("auth_screen")
            }
        }
    }
}