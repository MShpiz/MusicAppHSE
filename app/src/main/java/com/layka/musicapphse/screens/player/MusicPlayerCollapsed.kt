package com.layka.musicapphse.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedState(
    onClicked: () -> Unit,
    playerModel: PlayerModel = hiltViewModel(),
    playerPosition: MutableState<Float>
) {
    Column {
        Slider(
            value = playerPosition.value / 1000,
            onValueChange = { playerModel.queueModel.changePosition((it * 1000).toLong()) },
            valueRange = 0f..(playerModel.queueModel.currentTrack.value?.duration?.toFloat() ?: 0f),
            enabled = false,
            track = { currSliderState ->
                val fraction = remember {
                    derivedStateOf {
                        (currSliderState.value - currSliderState.valueRange.start) / (currSliderState.valueRange.endInclusive - currSliderState.valueRange.start)
                    }
                }

                Box(Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .fillMaxWidth(fraction.value)
                            .background(colorResource(id = R.color.white))
                            .align(Alignment.TopStart)
                            .height(6.dp)
                            .background(colorResource(id = R.color.purple_200), CircleShape)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(1f - fraction.value)
                            .background(colorResource(id = R.color.white))
                            .align(Alignment.TopEnd)
                            .height(6.dp)
                            .background(
                                colorResource(id = R.color.LightlyTransparentGrey),
                                CircleShape
                            )
                    )
                }
            },
            thumb = {},
            modifier = Modifier
                .background(colorResource(id = R.color.white))
                .fillMaxWidth(0.98f)
                .height(5.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .background(colorResource(id = R.color.white))
            .padding(top = 23.dp, bottom = 12.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .httpHeaders(playerModel.headers.value)
                    .data(playerModel.queueModel.currentTrack.value?.albumCover) // TODO ("заменить на рабочую ссылку")
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = playerModel.queueModel.currentTrack.value?.trackName ?: "",
                placeholder = painterResource(R.drawable.music_note), // TODO("заменить дефолтную картинку")
                error = painterResource(id = R.drawable.music_note),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(40.dp)
            )
            Column {
                Text(
                    text = playerModel.queueModel.currentTrack.value?.trackName ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Text(
                    text = playerModel.queueModel.currentTrack.value?.artists ?: "",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    playerModel.queueModel.playPrevious()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.skip_previous),
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            IconButton(
                onClick = {
                    if (playerModel.queueModel.isPlaying.value)
                        playerModel.queueModel.pausePlayer()
                    else playerModel.queueModel.startPlayer()
                }
            ) {
                if (!playerModel.queueModel.isPlaying.value)
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(id = R.string.play),
                        modifier = Modifier.fillMaxHeight()
                    )
                else
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.pause),
                        contentDescription = stringResource(id = R.string.pause),
                        modifier = Modifier.fillMaxHeight()
                    )
            }
            IconButton(
                onClick = {
                    playerModel.queueModel.playNext()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.skip_next),
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}