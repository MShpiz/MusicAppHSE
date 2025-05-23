package com.layka.musicapphse.services

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.layka.musicapphse.storage.RepoType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class TokenManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REPO_TYPE_KEY = stringPreferencesKey("repo_type")
    }

    fun getToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getRepoType(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[REPO_TYPE_KEY] ?: RepoType.HTTP.toString()
        }
    }

    suspend fun changeRepoType(type: RepoType) {
        context.dataStore.edit { preferences ->
            preferences[REPO_TYPE_KEY] = type.toString()
        }
    }
}