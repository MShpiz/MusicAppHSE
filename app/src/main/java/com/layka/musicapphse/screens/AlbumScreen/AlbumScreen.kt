package com.layka.musicapphse.screens.AlbumScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.customComposableElements.ScreenWithImageHeader.ScreenWithImageHeader
import com.layka.musicapphse.screens.Lists.TrackList.TrackList


@Composable
fun AlbumScreenHeader(
    albumCoverHeight: Int,
    albumName: String,
    artists: String,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(albumCoverHeight.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("127.0.0.1:8000") // TODO ("заменить на рабочую ссылку")
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = albumName,
            placeholder = painterResource(R.drawable.music_note), // TODO("заменить дефолтную картинку")
            error = painterResource(id = R.drawable.music_note),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.CenterHorizontally)
                .height(200.dp)
                .clip(RoundedCornerShape(15.dp))
        )

        Text(
            text = albumName,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = artists,
            fontSize = 20.sp,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(albumData: AlbumData, navController: NavController) {
    val albumCoverHeight = 370
    val header = @Composable {
        AlbumScreenHeader(
            albumCoverHeight,
            albumData.name,
            albumData.artist
        )
    }
    val body = @Composable {
        TrackList(
            trackData = albumData.tracks,
            showCover = false,
            showArtistName = false,
            navController = navController
        )
    }

    val showScreenName = remember { mutableStateOf(false) }

    val onBottomSheetToggle = { showScreenName.value = !showScreenName.value }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.LightlyTransparentGrey),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    if (showScreenName.value)
                        Text(albumData.name)
                }
            )
        }
    ) { innerPadding ->
        ScreenWithImageHeader(
            header = header,
            body = body,
            headerHeight = albumCoverHeight,
            padding = innerPadding
        )
    }

}