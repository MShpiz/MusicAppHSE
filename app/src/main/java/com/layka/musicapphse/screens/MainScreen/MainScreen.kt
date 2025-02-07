package com.layka.musicapphse.screens.MainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.stringResource
import com.layka.musicapphse.R
import com.layka.musicapphse.screens.AlbumListScreen.AlbumListData
import com.layka.musicapphse.screens.Lists.AlbumList.AlbumVerticalList
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData
import com.layka.musicapphse.screens.Lists.TrackList.TrackList

@Composable
fun MainScreen(trackData: List<MusicTrackData>, albums: List<AlbumListData>) {
    Scaffold() { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text= stringResource(id =  R.string.all_tracks))
            }
            TrackList(trackData = trackData, showCover = true)
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.all_albums))
            }
            AlbumVerticalList(albums = albums)
        }
    }
}