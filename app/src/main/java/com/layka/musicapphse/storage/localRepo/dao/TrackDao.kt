package com.layka.musicapphse.storage.localRepo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.layka.musicapphse.storage.localRepo.entities.TrackEntity

@Dao
interface TrackDao {
    @Insert
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Delete
    suspend fun deleteTrackById(trackEntity: TrackEntity)

    @Query("SELECT * FROM tracks")
    suspend fun getAll(): List<TrackEntity>?

    @Transaction
    @Query("SELECT * FROM tracks WHERE artist = :artist")
    suspend fun getTracksByArtist(artist: String): List<TrackEntity>?

    @Transaction
    @Query("SELECT * FROM tracks WHERE trackId = :id")
    suspend fun getTrackByTrackId(id: Int): TrackEntity?

    @Transaction
    @Query("SELECT * FROM tracks WHERE name = :name")
    suspend fun getTracksByName(name: String): List<TrackEntity>?
}