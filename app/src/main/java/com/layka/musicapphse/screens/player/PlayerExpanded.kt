package com.layka.musicapphse.screens.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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


@Composable
fun PlayerExpanded(trackData: MusicTrackData) {
    val sliderPosition = remember { mutableFloatStateOf(0f) }
    val trackDurationStr = "${trackData.duration / 60}:${trackData.duration % 60}"
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.height(70.dp)
                .weight(1f)
        ) {
            Box(modifier = Modifier.weight(1.0f)) {
                IconButton(
                    onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }

            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .weight(4.0f)
            //.padding(top = 100.dp, bottom = 30.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("127.0.0.1:8000") // TODO ("заменить на рабочую ссылку")
                    .networkCachePolicy(CachePolicy.ENABLED).memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = trackData.trackName,
                placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
                error = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .align(Alignment.Center)

                    .height(300.dp)

            )
        }
        Column(Modifier.fillMaxWidth()) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = trackData.trackName,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = trackData.artists.fastJoinToString { it.second },
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
                value = sliderPosition.floatValue,
                onValueChange = { sliderPosition.floatValue = it },
                valueRange = 0f..trackData.duration.toFloat(),

                )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "${sliderPosition.floatValue.toInt() / 60}:${sliderPosition.floatValue.toInt() % 60}",
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1.0f)
                    //modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = trackDurationStr,
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
                onClick = { /*TODO*/ }, modifier = Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }


        IconButton(
            onClick = { /*TODO*/ },
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1.0f)
                .padding(bottom = 30.dp)
        ) { // shuffle btn
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.back),
                modifier = Modifier.fillMaxHeight()
            )
        }

    }
}

