package com.layka.musicapphse.services

data class RegisterResult(val success: Boolean, val data: RegisterInfo) {
    data class RegisterInfo(val id: Int, val username: String, val email: String)
}