package com.layka.musicapphse.screens.searchScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val foundTracks = mutableStateListOf<MusicTrackData>()

    fun getTracks(query: String, navController: NavController) {
        var exception = false
        foundTracks.clear()
        viewModelScope.launch {

            try {
                repository.findTracksByParams(query, "", "", "").forEach { foundTracks.add(it) }
            } catch (e: IllegalAccessException) {
                exception = true
            }
        }.invokeOnCompletion {
            if (exception) {
                navController.navigate("auth_screen")
            }
        }
    }
}