package com.layka.musicapphse.screens.MainScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistList
import com.layka.musicapphse.screens.Lists.PlaylistList.PlaylistList
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@Composable
fun MainScreen(
    navController: NavController,
    musicDataViewModel: MainViewModel = hiltViewModel()
) {
    val gotTracks = remember { mutableStateOf(false) }
    if (!gotTracks.value) {
        Log.d("MAIN_SCREEN", gotTracks.value.toString())
        musicDataViewModel.getAllData(navController)
        gotTracks.value = true
        Log.d("MAIN_SCREEN", gotTracks.value.toString())
    }
    Scaffold(topBar = {
        TopBar(
            navController = navController,
            showScreenName = remember { mutableStateOf(false) },
            showBackArrow = false
        )
    },
        bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
        LazyColumn(Modifier.padding(innerPadding).padding(bottom=100.dp)) {
            item {
                TextButton(onClick = { navController.navigate("all_tracks_screen") }) {
                    Text(text = stringResource(id = R.string.all_tracks))
                }
                TrackList(
                    trackData = musicDataViewModel.trackData,
                    showCover = true,
                    navController = navController,
                    showArtistName = true
                )
                TextButton(onClick = { navController.navigate("all_playlists_screen") }) {
                    Text(text = stringResource(id = R.string.all_playlists))
                }
                PlaylistList(musicDataViewModel.playListData, navController)
                TextButton(onClick = { navController.navigate("all_artists_screen") }) {
                    Text(text = stringResource(id = R.string.all_artists))
                }
                ArtistList(artistData = musicDataViewModel.artistData, navController)
            }
        }
    }
}