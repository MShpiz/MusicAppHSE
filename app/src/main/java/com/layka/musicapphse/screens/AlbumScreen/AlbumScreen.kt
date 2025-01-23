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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastJoinToString
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.customComposableElements.ScreenWithImageHeader.ScreenWithImageHeader
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
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
            placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
            error = painterResource(id = R.drawable.ic_launcher_background),
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
fun AlbumScreen(albumData: AlbumData, navController: NavController,) {
    val albumCoverHeight = 370
    val header = @Composable {
        AlbumScreenHeader(
            albumCoverHeight,
            albumData.name,
            albumData.artists.fastJoinToString { it.second }
        )
    }
    val body = @Composable {
        TrackList(
            trackData = albumData.tracks,
            showCover = false,
            showArtistName = false
        )
    }

    val showScreenName = remember{ mutableStateOf(false) }

    val onBottomSheetToggle = {showScreenName.value = !showScreenName.value}

    Scaffold (
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
            padding = innerPadding,
            onBottomSheetToggle = onBottomSheetToggle
        )
    }

}


@Preview
@Composable
fun AlbumScreenPreview() {
    val tracks = MusicTrackData(
        1,
        "Aaaaaaa Aaaaa",
        listOf(Pair(1, "Artist 1")),
        100
    )
    val data = AlbumData(
        1,
        "Крик души",
        listOf(Pair(1, "Типикал вышкинец")),
        listOf(
            tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks,
            tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks, tracks
        ),
    )
    // AlbumScreen(albumData = data, null)
}