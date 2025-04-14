package com.layka.musicapphse.screens.Lists.TrackList.AllTrackListScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TrackListScreenViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    val mutableStateList = mutableStateListOf<MusicTrackData>()

    init {
        updateList()
    }

    private fun updateList() {
        mutableStateList.clear()
        viewModelScope.launch {
            repository.getAllTracks().forEach { mutableStateList.add(it) }
        }
    }
}