package com.layka.musicapphse.screens.Lists.TrackList

data class MusicTrackData(
    val trackId: Int,
    val trackName: String,
    val artists: List<Pair<Int, String>>, // id and name of artists
    val duration: Int, // продолжительность трека в секундах
    val albumCover: String? = null
)