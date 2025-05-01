package com.layka.musicapphse.screens.AlbumScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.PlaylistScreen.PlaylistScreenViewModel
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar


@Composable
fun PlaylistScreenHeader(
    playlistName: String,
    description: String,
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = playlistName,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = description,
            fontSize = 20.sp,
            style = TextStyle(color = Color.Gray),
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun PlaylistScreen(
    playlistId: Int,
    navController: NavController,
    playlistScreenViewModel: PlaylistScreenViewModel = hiltViewModel()
) {
    val isRemovingTracks = remember {
        mutableStateOf(false)
    }
    val callBack = {isRemovingTracks.value = false}
    val playlistData = remember {
        playlistScreenViewModel.playlistData
    }
    val gotData = remember {
        mutableStateOf(false)
    }
    if (!gotData.value) {
        gotData.value = true
        playlistScreenViewModel.getPlaylist(playlistId, navController)
    }
    val showEditScreen = remember {
        mutableStateOf(false)
    }
    val playlistName = remember { mutableStateOf(playlistData.value.name) }
    val playlistDescription = remember { mutableStateOf(playlistData.value.description) }
    val header = @Composable {
        PlaylistScreenHeader(
            playlistData.value.name,
            playlistData.value.description
        )
    }
    val body = @Composable {
        TrackList(
            trackData = playlistData.value.tracks,
            showCover = false,
            showArtistName = false,
            navController = navController,
            onChecked = { id, checked ->
                playlistScreenViewModel.addTrack(id, checked)
            },
            showCheckbox = isRemovingTracks.value
        )
    }
    val showScreenName = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = showScreenName,
                screenName = playlistData.value.name
            )
        },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            if (!isRemovingTracks.value) {
                FloatingActionButton(onClick = { showEditScreen.value = true }) {
                    Icon(Icons.Default.Create, contentDescription = "Add")
                }
            } else {
                FloatingActionButton(onClick = { playlistScreenViewModel.removeTracksFromPlaylist(navController, callBack) }) {
                    Icon(Icons.Default.Done, contentDescription = "Save")
                }
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            header()
            body()
        }
    }

    if (showEditScreen.value) {
        playlistName.value = playlistData.value.name
        playlistDescription.value = playlistData.value.description
        Dialog(onDismissRequest = { showEditScreen.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 5.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.update_playlist),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = playlistName.value,
                        onValueChange = { playlistName.value = it },
                        label = { Text(stringResource(id = R.string.name)) },
                        maxLines = 1,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 1.dp)
                    )
                    TextField(
                        value = playlistDescription.value,
                        onValueChange = { playlistDescription.value = it },
                        label = { Text(stringResource(id = R.string.description)) },
                        maxLines = 10,
                        modifier = Modifier.padding(vertical = 2.dp, horizontal = 1.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { showEditScreen.value = false },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(id = R.string.cancel))
                        }
                        TextButton(
                            onClick = {
                                showEditScreen.value = false
                                isRemovingTracks.value = true
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Remove tracks")
                        }
                        TextButton(
                            onClick = {
                                showEditScreen.value = false
                                playlistScreenViewModel.editPlaylist(
                                    playlistName.value,
                                    playlistDescription.value,
                                    navController
                                )
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(stringResource(id = R.string.update))
                        }
                    }
                }
            }
        }
    }
}