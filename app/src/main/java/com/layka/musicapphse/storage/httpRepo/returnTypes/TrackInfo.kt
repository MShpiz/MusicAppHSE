package com.layka.musicapphse.storage.httpRepo.returnTypes

data class TrackInfo(
    val album: String,
    val artist: String,
    val createdAt: String,
    val duration: Int,
    val genre: String,
    val id: Int,
    val image_url: String,
    val title: String,
    val uploadedBy: Int
)
