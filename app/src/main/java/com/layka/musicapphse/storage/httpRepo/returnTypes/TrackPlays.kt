package com.layka.musicapphse.storage.httpRepo.returnTypes

data class TrackPlays(
    val success: Boolean, val data: List<TrackPlaysInfo>
) {
    data class TrackPlaysInfo(
        val artist: String,
        val id: Int,
        val playCount: Int,
        val title: String
    )
}
