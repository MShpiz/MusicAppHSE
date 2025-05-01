package com.layka.musicapphse.storage.httpRepo.returnTypes

data class RecentTracksResponse(val success: Boolean, val data: List<RecentTracksInfo>) {
    data class RecentTracksInfo(
        val ID: Int,
        val CreatedAt: String,
        val UpdatedAt: String,
        val DeletedAt: String,
        val Title: String,
        val Artist: String,
        val Album: String,
        val Genre: String,
        val Duration: Int,
        val ImagePath: String,
        val FilePath: String,
        val UploadedBy: Int
    )
}