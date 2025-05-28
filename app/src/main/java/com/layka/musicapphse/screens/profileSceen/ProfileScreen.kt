package com.layka.musicapphse.screens.profileSceen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.Lists.ArtistList.ArtistList
import com.layka.musicapphse.screens.Lists.TrackList.TrackList
import com.layka.musicapphse.screens.utils.BottomBar
import com.layka.musicapphse.screens.utils.TopBar

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val gotData = remember {
        mutableStateOf(false)
    }

    if (!gotData.value) {
        gotData.value = true
        viewModel.getProfileData(navController)
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                showScreenName = remember { mutableStateOf(true) },
                screenName = "Profile",
                showBackArrow = false
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(bottom=100.dp)
                .fillMaxWidth()
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(null)
                            // .httpHeaders(headers = headers)
                            .networkCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = "profile image",
                        placeholder = painterResource(R.drawable.profile_picture),
                        error = painterResource(R.drawable.profile_picture),
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .height(150.dp)
                    )
                    Text(
                        text = viewModel.profileInfo.value.username,
                        fontSize = 35.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Recently listened tracks:",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 5.dp).padding(horizontal = 20.dp)
                )
            }
            item {
                TrackList(
                    trackData = viewModel.recentTracks,
                    showCover = false,
                    showArtistName = true,
                    navController = navController,
                    showMenuBtn = false
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Text(
                    text = "Recently listened artists:",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            item {
                ArtistList(artistData = viewModel.recentArtists, navController = navController)
            }
        }

    }
}