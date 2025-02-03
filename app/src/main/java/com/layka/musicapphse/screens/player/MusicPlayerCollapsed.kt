package com.layka.musicapphse.screens.player

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastJoinToString
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedState(trackData: MusicTrackData, modifier: Modifier) {
    val sliderPosition = remember { mutableFloatStateOf(0f) }
    Column (Modifier.then(modifier)) {
        Slider(
            value = sliderPosition.floatValue,
            onValueChange = { sliderPosition.floatValue = it },
            valueRange = 0f..trackData.duration.toFloat(),
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
                            .align(Alignment.CenterStart)
                            .height(6.dp)
                            .background(colorResource(id = R.color.purple_200), CircleShape)
                    )
                    Box(
                        Modifier
                            .fillMaxWidth(1f - fraction.value)
                            .align(Alignment.CenterEnd)
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
                .fillMaxWidth(0.98f)
                .align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("127.0.0.1:8000") // TODO ("заменить на рабочую ссылку")
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = trackData.trackName,
                placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
                error = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .height(40.dp)
            )
            Column {
                Text(
                    text = trackData.trackName,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Text(
                    text = trackData.artists.fastJoinToString { it.second },
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}