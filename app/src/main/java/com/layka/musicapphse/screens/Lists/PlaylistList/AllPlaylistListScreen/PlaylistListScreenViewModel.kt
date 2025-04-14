package com.layka.musicapphse.screens.Lists.TrackList.AllTrackListScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layka.musicapphse.screens.Lists.PlaylistList.ShortPlaylistData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlaylistListScreenViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    val mutableStateList = mutableStateListOf<ShortPlaylistData>()

    init {
        updateList()
    }

    private fun updateList() {
        mutableStateList.clear()
        viewModelScope.launch {
            repository.getUserPlaylists().forEach { mutableStateList.add(it) }
        }
    }
}