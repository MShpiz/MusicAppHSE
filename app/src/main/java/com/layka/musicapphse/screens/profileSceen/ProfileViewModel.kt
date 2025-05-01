package com.layka.musicapphse.screens.profileSceen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.screens.player.QueueModel
import com.layka.musicapphse.storage.Repository
import com.layka.musicapphse.storage.httpRepo.returnTypes.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import kotlin.math.min

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository, val queueModel: QueueModel) : ViewModel() {
    val profileInfo =
        mutableStateOf<ProfileResponse.ProfileInfo>(ProfileResponse.ProfileInfo("", -1, ""))
    val myTracks = mutableStateListOf<MusicTrackData>()
    val recentTracks = mutableStateListOf<MusicTrackData>()
    val recentArtists = mutableStateListOf<ArtistData>()


    fun getProfileData(navController: NavController) {
        var gotException = false
        viewModelScope.launch {
            try {
                profileInfo.value = repository.getProfile()

                repository.getUserTracks(profileInfo.value.id).map { myTracks.add(it) }

                repository.getRecentTracks().forEach { recentTracks.add(it) }
                recentTracks.removeRange(min(10, recentTracks.size), recentTracks.size)

                repository.getRecentArtist().forEach { recentArtists.add(it) }
                recentArtists.removeRange(min(10, recentArtists.size), recentArtists.size)


            } catch (e: IllegalAccessException) {
                gotException = true
            }
        }.invokeOnCompletion {
            if (gotException) {
                navController.navigate("auth_screen")
            }
        }
    }
}