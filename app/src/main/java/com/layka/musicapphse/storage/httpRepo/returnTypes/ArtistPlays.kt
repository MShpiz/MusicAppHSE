package com.layka.musicapphse.storage.httpRepo.returnTypes

data class ArtistPlays(
    val success: Boolean,
    val data: List<ArtistPlaysInfo>
) {
    data class ArtistPlaysInfo(
        val artist: String,
        val playCount: Int
    )
}