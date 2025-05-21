package com.layka.musicapphse.screens.artistScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.customComposableElements.ScreenWithImageHeader.ScreenWithImageHeader
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    artistName: String,
    navController: NavController,
    viewModel: ArtistViewModel = hiltViewModel()
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val headerHeight = 300.dp
    val modalBottomSheetHeight = screenHeight - headerHeight + 200.dp

    val gotData = remember {
        mutableStateOf(false)
    }
    if (!gotData.value) {
        gotData.value = true
        viewModel.getArtistData(artistName, navController)
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = artistName
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        ScreenWithImageHeader(header = {
            val cover = remember { mutableStateOf<String?>(null) }
                if (viewModel.artistData.isNotEmpty()) {
                    cover.value = viewModel.artistData.get(0).albumCover
                }
                ArtistHeader(
                    name = artistName,
                    height = headerHeight,
                    image = cover.value,
                    headers = viewModel.headers.value
                )
        }, body = {
            Box(
                    Modifier
                        .padding(bottom = 100.dp)
                        .height(screenHeight)
                ) {
                    ArtistScreenBody(
                        viewModel.artistData,
                        navController
                    )
                }
        }, padding = padding)
//        BottomSheetScaffold(
//            scaffoldState = scaffoldState,
//            sheetPeekHeight = modalBottomSheetHeight,
//            sheetSwipeEnabled = scrollState.value == 0,
//            sheetDragHandle = null,
//            sheetContent = {
//                Box(
//                    Modifier
//                        .padding(bottom = 100.dp)
//                        .height(screenHeight)
//                ) {
//                    ArtistScreenBody(
//                        viewModel.artistData,
//                        navController
//                    )
//                }
//            },
//            modifier = Modifier.padding(padding).padding(bottom=100.dp)
//        ) { bottomSheetInnerPadding ->
//            Box(
//                modifier = Modifier
//                    .padding(bottomSheetInnerPadding)
//            ) {
//                val cover = remember { mutableStateOf<String?>(null) }
//                if (viewModel.artistData.isNotEmpty()) {
//                    cover.value = viewModel.artistData.get(0).albumCover
//                }
//                ArtistHeader(
//                    name = artistName,
//                    height = headerHeight,
//                    image = cover.value,
//                    headers = viewModel.headers.value
//                )
//            }
//        }
    }
}

@Composable
private fun ArtistHeader(name: String, height: Dp, image: String?, headers: NetworkHeaders) {
    Column(
        Modifier
            .fillMaxWidth()
            .height(height)
            .border(1.dp, color = Color.Blue)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .httpHeaders(headers = headers)
                .networkCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = name,
            placeholder = painterResource(R.drawable.profile_picture),
            error = painterResource(id = R.drawable.profile_picture),
            modifier = Modifier
                .border(1.dp, Color.Red)
                .padding(top = 7.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .height(185.dp)
        )
        Text(
            text = name,
            fontSize = 30.sp,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ArtistScreenBody(tracks: List<MusicTrackData>, navController: NavController) {
    TrackList(trackData = tracks, showCover = false, navController = navController)
}