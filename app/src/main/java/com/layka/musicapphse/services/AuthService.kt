package com.layka.musicapphse.services

import android.util.Log
import jakarta.inject.Inject
import java.net.ConnectException

class AuthService @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) {
    suspend fun register(email: String, password: String, username: String): AuthResult {
        try {
            val response = api.register(RegisterData(email, password, username))
            return when (response.code()) {
                201 -> login(email, password)
                409 -> AuthResult.CONFLICT
                400 -> AuthResult.UNKNOWN
                else -> AuthResult.UNKNOWN
            }
        } catch (e: ConnectException) {
            Log.v("IS_AUTH", e.message.toString())
            return AuthResult.NO_NETWORK
        } catch (e: Exception) {
            Log.v("IS_AUTH", e.message.toString())
            return AuthResult.UNKNOWN
        }
    }

    suspend fun login(email: String, password: String): AuthResult {
        try {
            val response = api.login(LoginData(email, password))
            if (response.isSuccessful && response.body()?.data?.token != null) {
                tokenManager.saveToken(response.body()!!.data.token)
            }
            Log.d("IS_AUTH", response.toString())
            // if (response.body()?.data?.token == null) return AuthResult.UNKNOWN
            return when (response.code()) {
                200 -> AuthResult.OK
                400 -> AuthResult.WRONG_EMAIL_PASSWORD
                401 -> AuthResult.UNKNOWN
                else -> AuthResult.UNKNOWN
            }
        } catch (e: ConnectException) {
            Log.v("IS_AUTH", e.message.toString())
            return AuthResult.NO_NETWORK
        } catch (e: Exception) {
            Log.v("IS_AUTH", e.message.toString())
            return AuthResult.UNKNOWN
        }
    }
}