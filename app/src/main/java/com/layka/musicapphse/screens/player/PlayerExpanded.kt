package com.layka.musicapphse.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.TrackList.TrackList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerExpanded(
    playerModel: PlayerModel,
    collapsePlayer: () -> Unit,
    navController: NavController,
    playerPosition: MutableState<Float>
) {
    val showQueue = remember {
        mutableStateOf(false)
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.0f)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = {
                            collapsePlayer()
                            showQueue.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
                IconButton(
                    onClick = {
                        showQueue.value = !showQueue.value
                    }, modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
            Box(
                modifier = Modifier.weight(7f)
            ) {
                if (!showQueue.value) {
                    PlayerView(playerModel, playerPosition, navController)
                } else {
                    MusicQueue(playerModel = playerModel)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerView(
    playerModel: PlayerModel,
    playerPosition: MutableState<Float>,
    navController: NavController
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val trackDurationStr = remember {
        mutableStateOf(
            ((playerModel.queueModel.currentTrack.value?.duration ?: 0) / 60).toString() +
                    ":" +
                    ((playerModel.queueModel.currentTrack.value?.duration ?: 0) % 60).toString()
                        .padStart(2, '0')
        )
    }
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(4.0f)
            //.padding(top = 100.dp, bottom = 30.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .httpHeaders(playerModel.headers.value)
                    .data(playerModel.queueModel.currentTrack.value?.albumCover)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = playerModel.queueModel.currentTrack.value?.trackName ?: "",
                placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
                error = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .padding()
                    .align(Alignment.Center)
                    .height(300.dp)

            )
        }

        Column(Modifier.fillMaxWidth()) {
            TextButton(onClick = {}) {
                Text(
                    text = playerModel.queueModel.currentTrack.value?.trackName ?: "",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            TextButton(onClick = { navController.navigate("artist/${playerModel.queueModel.currentTrack.value?.artists ?: ""}") }) {
                Text(
                    text = playerModel.queueModel.currentTrack.value?.artists ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
        ) {
            Slider(
                value = playerPosition.value / 1000,
                onValueChange = { value ->
                    playerModel.queueModel.changePosition((value * 1000).toLong())
                },
                valueRange = 0f..(playerModel.queueModel.currentTrack.value?.duration?.toFloat()
                    ?: 0.0f),
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = interactionSource,
                        thumbSize = DpSize(18.dp, 18.dp)
                    )
                }

            )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "${
                        (playerPosition.value.toInt() / 60000)
                    }:${
                        (playerPosition.value.toInt() / 1000 % 60).toString().padStart(2, '0')
                    }",
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1.0f)
                    //modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = trackDurationStr.value,
                    fontSize = 15.sp,

                    )
            }
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.0f)

        ) {
            IconButton(
                onClick = { playerModel.queueModel.playPrevious() }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.skip_previous),
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
            IconButton(
                onClick = {
                    if (!playerModel.queueModel.isPlaying.value)
                        playerModel.queueModel.startPlayer()
                    else {
                        playerModel.queueModel.pausePlayer()
                    }
                },
                modifier = Modifier.fillMaxHeight()
            ) {
                if (!playerModel.queueModel.isPlaying.value)
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.pause),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
            IconButton(
                onClick = { playerModel.queueModel.playNext() },
                Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.skip_next),
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )

            }
        }

        Row(
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                onClick = { playerModel.queueModel.shuffle() },
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1.0f)
                    .padding(bottom = 30.dp)
            ) { // shuffle btn
                if (!playerModel.queueModel.shuffleOn.value) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.shuffle),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.shuffle_on),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }

            IconButton(
                onClick = { playerModel.queueModel.toggleRepeatMode() },
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1.0f)
                    .padding(bottom = 30.dp)
            ) { // repeat btn
                if (!playerModel.queueModel.repeatOn.value) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.repeat),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.repeat_on),
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }

    }
}


@Composable
fun MusicQueue(playerModel: PlayerModel) {
    TrackList(
        trackData = playerModel.queueModel.trackList.value, showCover = false,
        navController = null, showMenuBtn = false, showArtistName = true
    )
}

