package com.layka.musicapphse.storage.localRepo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.layka.musicapphse.storage.localRepo.entities.PlaylistEntity
import com.layka.musicapphse.storage.localRepo.entities.TrackEntity

@Dao
interface PlaylistDao {
    @Insert
    fun insertPlayList(playlistEntity: PlaylistEntity)

    @Delete
    fun deletePlaylist(playlistEntity: PlaylistEntity)

    @Update
    suspend fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlists")
    suspend fun getAll(): List<PlaylistEntity>?

    @Transaction
    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    suspend fun getPlaylistById(id: Int): PlaylistEntity?
}