package com.layka.musicapphse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.layka.musicapphse.screens.AddTrackToPlaylistScreen.AddTrackToPlaylistScreen
import com.layka.musicapphse.screens.AlbumScreen.PlaylistScreen
import com.layka.musicapphse.screens.AuthScreen.AuthScreen
import com.layka.musicapphse.screens.Lists.ArtistList.AllArtistList.AllArtistListScreen
import com.layka.musicapphse.screens.Lists.TrackList.AllTrackListScreen.AllPlaylistListScreen
import com.layka.musicapphse.screens.Lists.TrackList.AllTrackListScreen.AllTrackListScreen
import com.layka.musicapphse.screens.MainScreen.MainScreen
import com.layka.musicapphse.screens.artistScreen.ArtistScreen
import com.layka.musicapphse.screens.player.MusicPlayer
import com.layka.musicapphse.screens.profileSceen.ProfileScreen
import com.layka.musicapphse.screens.searchScreen.SearchScreen
import com.layka.musicapphse.screens.settingsScreen.SettingsScreen
import com.layka.musicapphse.ui.theme.MusicAppHSETheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext

        enableEdgeToEdge()
        setContent {
            val musicPermission = rememberPermissionState(
                android.Manifest.permission.READ_MEDIA_AUDIO
            )
            LaunchedEffect(Unit) {
                if (!musicPermission.status.isGranted) {
                    musicPermission.launchPermissionRequest()
                }
            }

            MusicAppHSETheme {

                Box(Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            MainScreen(
                                navController = navController
                            )
                        }
                        composable("auth_screen") {
                            AuthScreen(navController = navController)
                        }

                        composable("all_tracks_screen") {
                            AllTrackListScreen(navController = navController)
                        }
                        composable("all_playlists_screen") {
                            AllPlaylistListScreen(navController = navController)
                        }
                        composable("all_artists_screen") {
                            AllArtistListScreen(navController = navController)
                        }
                        composable("playlist/{id}", arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                            nullable = false
                        })) {
                            val id = remember {
                                it.arguments?.getInt("id") ?: -1
                            }
                            PlaylistScreen(playlistId = id, navController = navController)
                        }

                        composable("trackPlaylist/{id}", arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                            nullable = false
                        })) {
                            val id = remember {
                                it.arguments?.getInt("id") ?: -1
                            }
                            AddTrackToPlaylistScreen(
                                trackId = id,
                                navController = navController
                            )
                        }

                        composable("artist/{name}", arguments = listOf(navArgument("name") {
                            type = NavType.StringType
                            nullable = false
                        })) {
                            val name = remember {
                                it.arguments?.getString("name") ?: ""
                            }
                            ArtistScreen(artistName = name, navController = navController)
                        }

                        composable("search_screen") {
                            SearchScreen(navController = navController)
                        }
                        composable("profile_screen") {
                            ProfileScreen(navController = navController)
                        }
                        composable("settings_screen") {
                            SettingsScreen(navController = navController)
                        }
                    }
                    if (navController.currentBackStackEntryAsState().value.toString() != "auth_screen") {
                        MusicPlayer(navController = navController)
                    }
                }

            }
        }
    }
}