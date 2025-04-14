package com.layka.musicapphse.screens.Lists.TrackList

data class MusicTrackData(
    val trackId: Int,
    val trackName: String,
    val artists: String, // id and name of artists
    val duration: Int, // продолжительность трека в секундах
    val uri: String,
    val albumCover: String? = null
)