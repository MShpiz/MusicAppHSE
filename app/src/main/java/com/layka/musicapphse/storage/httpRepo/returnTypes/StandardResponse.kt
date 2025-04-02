package com.layka.musicapphse.storage.httpRepo.returnTypes

data class StandardResponse(
    val data: Any,
    val error: String,
    val message: String,
    val success: Boolean
)
