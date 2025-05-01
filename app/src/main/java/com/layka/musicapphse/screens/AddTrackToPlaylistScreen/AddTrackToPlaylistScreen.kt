package com.layka.musicapphse.screens.AddTrackToPlaylistScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.layka.musicapphse.screens.Lists.PlaylistList.PlaylistList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@Composable
fun AddTrackToPlaylistScreen(
    trackId: Int,
    navController: NavController,
    viewModel: AddTrackToPlaylistViewModel = hiltViewModel()
) {
    val gotPlaylists = remember {
        mutableStateOf(false)
    }
    if (!gotPlaylists.value) {
        gotPlaylists.value = true
        viewModel.getPlaylists(navController)
    }
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(false) })
        },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addTrackToPlaylists(
                    trackId,
                    navController
                )
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "save")
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            PlaylistList(
                playLists = viewModel.playlists,
                navController = navController,
                showCheckBox = true,
                onCheckBoxChecked = { id, checked -> viewModel.addPlaylist(id, checked) })
        }

    }

}