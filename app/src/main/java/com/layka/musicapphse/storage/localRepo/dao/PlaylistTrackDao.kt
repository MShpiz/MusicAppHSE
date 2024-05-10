package com.layka.musicapphse.storage.localRepo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.layka.musicapphse.storage.localRepo.entities.PlaylistWithTracks

@Dao
interface PlaylistTrackDao {
    @Query("SELECT * FROM playlists WHERE playlistId = :id")
    fun getPlaylistWithTracksByPlaylistId(id: Int): PlaylistWithTracks?

    @Transaction
    @Query("DELETE FROM playlisttrack WHERE playlistId = :playlistId AND trackId = :trackId")
    fun deleteTrackFromPlaylist(playlistId: Int, trackId: Int)

    @Transaction
    @Query("INSERT INTO playlisttrack values(:playlistId, :trackId)")
    fun addTrackToPlaylist(playlistId: Int, trackId: Int)
}