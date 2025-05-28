package com.layka.musicapphse.screens.Lists.TrackList.AllTrackListScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@Composable
fun AllTrackListScreen(
    navController: NavController,
    viewModel: TrackListScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = stringResource(id = R.string.all_tracks)
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TrackList(
                trackData = viewModel.mutableStateList, showCover = true, showArtistName = true,
                navController = navController
            )
        }
    }
}