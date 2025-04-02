package com.layka.musicapphse.storage.httpRepo.returnTypes

data class PlaylistInfo(
    val createdAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val tracks: List<TrackInfo>?
)
