package com.layka.musicapphse.screens.AlbumScreen

import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData

data class PlaylistData(
    val createdAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val tracks: List<MusicTrackData>
)
