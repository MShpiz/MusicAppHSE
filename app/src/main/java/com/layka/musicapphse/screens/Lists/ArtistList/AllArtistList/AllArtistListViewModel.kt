package com.layka.musicapphse.screens.Lists.ArtistList.AllArtistList

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AllArtistListViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    val mutableStateList = mutableStateListOf<ArtistData>()

    init {
        updateList()
    }

    private fun updateList() {
        mutableStateList.clear()
        viewModelScope.launch {
            repository.getAllTracks().map { it.artists }.toSet()
                .forEach { mutableStateList.add(ArtistData(it)) }
        }
    }
}