package com.layka.musicapphse.screens.artistScreen

import com.layka.musicapphse.screens.AlbumListScreen.AlbumListData
import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData

data class ArtistScreenData (
    val id: Int,
    val name: String,
    val tracks: List<MusicTrackData>,
    val albums: List<AlbumListData>
)
