package com.layka.musicapphse.services

data class LoginResult(
    val success: Boolean,
    val data: TokenData
) {
    data class TokenData(val token: String)
}
