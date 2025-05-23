package com.layka.musicapphse.screens.AlbumScreen

import com.layka.musicapphse.screens.Lists.TrackList.MusicTrackData

data class AlbumData(
    val id: Int,
    val name: String,
    val artist: String,
    val tracks: List<MusicTrackData>,
    val cover: String? = null
)
