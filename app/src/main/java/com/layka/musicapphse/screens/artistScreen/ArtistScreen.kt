package com.layka.musicapphse.screens.artistScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.layka.musicapphse.R
import com.layka.musicapphse.customComposableElements.ScreenWithImageHeader.ScreenWithImageHeader
import com.layka.musicapphse.screens.AlbumListScreen.AlbumListData
import com.layka.musicapphse.screens.AlbumScreen.AlbumScreen
import com.layka.musicapphse.screens.Lists.AlbumList.AlbumSquareElement
import com.layka.musicapphse.screens.Lists.AlbumList.AlbumVerticalList
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.screens.Lists.TrackList.TrackElement
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    artistData: ArtistScreenData,
    navController: NavController? // TODO (make non nullbale after testing)
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val headerHeight = 450.dp
    val modalBottomSheetHeight = screenHeight - headerHeight

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scrollState = rememberScrollState()

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
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
                }
            )
        }
    ) { padding ->
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = modalBottomSheetHeight,
            sheetSwipeEnabled = scrollState.value == 0, // TODO("fix scroll")
            sheetDragHandle = null,
            sheetContent = { Box(Modifier.height(screenHeight - padding.calculateTopPadding())){ArtistScreenBody(artistData, scrollState) }},
            modifier = Modifier.padding(padding)
        ) { bottomSheetInnerPadding ->
            Box(
                modifier = Modifier
                    .padding(bottomSheetInnerPadding),
                contentAlignment = Alignment.Center
            ) {
                ArtistHeader(artistData.name, headerHeight)
            }
        }
    }
}

@Composable
private fun ArtistHeader(name:String, height: Dp) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(height)) {
        AsyncImage(
            model = "/",
            contentDescription = name,
            placeholder = painterResource(R.drawable.ic_launcher_background), // TODO("заменить дефолтную картинку")
            error = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .padding(top=7.dp)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .height(300.dp)
        )
        Text(
            text = name,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(bottom=20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun ArtistScreenBody(artistData: ArtistScreenData, scrollState: ScrollState) {
    val maxNumberOfTracks = remember {5}
    val maxNumberOfAlbums = remember {5}

    Column(modifier = Modifier
        .verticalScroll(scrollState)
    ) {
        TextButton(onClick = { /*TODO*/ }, Modifier.align(Alignment.End)) {
            Text(text = stringResource(id = R.string.all_tracks))
        }

        Column {
            artistData.tracks.subList(0, min(maxNumberOfTracks, artistData.tracks.size)).forEachIndexed { idx,  it ->
                TrackElement(
                    id = it.trackId,
                    name = it.trackName,
                    artistName = it.artists.map { it.second },
                    artistId = it.artists.map { it.first },
                    duration = it.duration,
                    index = idx + 1,
                    showCover = false,
                    showArtistName = false
                )
            }
        }

        TextButton(onClick = { /*TODO*/ }, Modifier.align(Alignment.End)) {
            Text(text = stringResource(id = R.string.all_albums))
        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            modifier = Modifier.height(500.dp)
        ) {
            items(artistData.albums) { it ->
                Box(Modifier.align(Alignment.CenterHorizontally)) {
                    AlbumSquareElement(name = it.name, artistName = it.artists)
                }
            }
        }

    }
}

@Preview
@Composable
fun ArtistScreenPreview() {
    val track = MusicTrackData(
        1,
        "Aaaaaaa Aaaaa",
        listOf(Pair(1, "Artist 1")),
        100
    )
    val tracks = listOf(
        track, track, track, track, track, track, track
    )
    val album = AlbumListData(1, "AAAAAAA", listOf("Artist 1"), null)
    val artistData = ArtistScreenData(
        1,
        "Типикал вышкинец",
        tracks,
        listOf(album, album, album, album, album, album)
    )
    ArtistScreen(artistData, null)
}