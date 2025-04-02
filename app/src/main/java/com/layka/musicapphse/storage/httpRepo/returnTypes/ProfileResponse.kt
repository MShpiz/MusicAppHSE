package com.layka.musicapphse.storage.httpRepo.returnTypes

data class ProfileResponse(
    val success: Boolean,
    val data: ProfileInfo
) {
    data class ProfileInfo(

        val email: String,
        val id: Int,
        val username: String

    )
}
