package com.layka.musicapphse.screens.Lists.TrackList

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.network.NetworkHeaders
import com.layka.musicapphse.screens.player.QueueModel
import com.layka.musicapphse.services.DownloadService
import com.layka.musicapphse.services.TokenManager
import com.layka.musicapphse.storage.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@HiltViewModel
class TrackListViewModel @Inject constructor(
    val queueModel: QueueModel,
    val repository: Repository,
    val tokenManager: TokenManager,
    val downloadService: DownloadService
) : ViewModel() {

    val headers = mutableStateOf(NetworkHeaders.Builder().build())
    fun deleteTrack(id: Int, navController: NavController) {
        var isAuth = true
        viewModelScope.launch {
            try {
                repository.deleteTrack(id)
            } catch (e: IllegalAccessException) {
                isAuth = false
            }
        }.invokeOnCompletion {
            if (!isAuth) {
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

    fun downloadTrack(trackData: MusicTrackData, callBack: (String)->Unit) {

        viewModelScope.launch {
            val filename: String? = downloadService.downloadTrack(trackData.uri, "${trackData.trackId}_${trackData.trackName}_${trackData.artists}.mp3")
            if (filename == null) {
                callBack("Failed to download track")
                return@launch
            }
            repository.saveTrack(
                MusicTrackData(
                    trackId = trackData.trackId,
                    trackName = trackData.trackName,
                    duration = trackData.duration,
                    artists = trackData.artists,
                    uri = filename,
                    albumCover = trackData.albumCover ?: ""
                )
            )
            callBack("Downloaded ${trackData.trackName}")
        }
    }
}