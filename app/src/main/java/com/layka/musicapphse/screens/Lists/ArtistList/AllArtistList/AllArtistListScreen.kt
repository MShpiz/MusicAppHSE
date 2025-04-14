package com.layka.musicapphse.screens.Lists.ArtistList.AllArtistList

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
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@Composable
fun AllArtistListScreen(
    navController: NavController,
    viewModel: AllArtistListViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = stringResource(id = R.string.all_artists)
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ArtistList(artistData = viewModel.mutableStateList, navController = navController)
        }
    }
}