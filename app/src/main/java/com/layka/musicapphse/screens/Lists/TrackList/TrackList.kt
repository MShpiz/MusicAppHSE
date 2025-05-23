package com.layka.musicapphse.screens.Lists.TrackList

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.layka.musicapphse.R


@Composable
fun TrackList(
    trackData: List<MusicTrackData>,
    showCover: Boolean,
    showArtistName: Boolean = false,
    navController: NavController?,
    showMenuBtn: Boolean = true,
    showCheckbox: Boolean = false,
    onChecked: (trackId: Int, checked: Boolean) -> Unit = { _, _ -> },
    enableClick: Boolean = true,
    viewModel: TrackListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val gotHeaders = remember {
        mutableStateOf(false)
    }
    if (!gotHeaders.value) {
        gotHeaders.value = true
        viewModel.getHeaders()
    }

    val showToast = fun(text: String) {
        Toast.makeText(
            context, text, Toast.LENGTH_SHORT
        ).show()
    }
    val data = remember {
        mutableStateOf(trackData)
    }
    if (data.value != trackData) {
        data.value = trackData
    }

    Box(
        Modifier
            .fillMaxWidth()
    ) {
        if (data.value.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_tracks),
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        } else {

            Column(Modifier.wrapContentHeight()) {
                data.value.forEachIndexed { idx, track ->
                    TrackElement(
                        id = track.trackId,
                        name = track.trackName,
                        artistName = track.artists,
                        duration = track.duration,
                        index = idx + 1, // тут нужен номер трека
                        showCover = showCover,
                        cover = track.albumCover,
                        headers = viewModel.headers.value,
                        showArtistName = showArtistName,
                        onClicked = fun() {
                            if (enableClick) {
                                viewModel.queueModel.setQueue(
                                    data.value,
                                    idx.toUInt()
                                )
                            }
                        },
                        onDeleteTrack = { id ->
                            viewModel.deleteTrack(id, navController!!)
                            val tmp = data.value.toMutableList()
                            tmp.removeAt(idx)
                            data.value = tmp
                        },
                        onAddToPlayList = {
                            navController?.navigate("trackPlaylist/${track.trackId}")
                                ?: Log.v("ADD_TRACK", "cant navigate to ${track.trackId}")
                        },
                        showMenuBtn = showMenuBtn,
                        showCheckBox = showCheckbox,
                        onChecked = onChecked,
                        onDownloadTrack = {

                            viewModel.downloadTrack(track, showToast)

                        }
                    )
                }
            }
        }
    }
}