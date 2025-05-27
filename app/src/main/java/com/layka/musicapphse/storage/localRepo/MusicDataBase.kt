package com.layka.musicapphse.storage.localRepo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.layka.musicapphse.storage.localRepo.dao.PlaylistDao
import com.layka.musicapphse.storage.localRepo.dao.PlaylistTrackDao
import com.layka.musicapphse.storage.localRepo.dao.TrackDao
import com.layka.musicapphse.storage.localRepo.dao.UpdateDao
import com.layka.musicapphse.storage.localRepo.entities.PlaylistEntity
import com.layka.musicapphse.storage.localRepo.entities.PlaylistTrack
import com.layka.musicapphse.storage.localRepo.entities.TrackEntity
import com.layka.musicapphse.storage.localRepo.entities.UpdateEntity


@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrack::class,
        UpdateEntity::class
    ],
    version = 1
)
abstract class MusicDataBase : RoomDatabase(), MusicDb {
    abstract override fun trackDao(): TrackDao
    abstract override fun playlistDao(): PlaylistDao
    abstract override fun playlistTrackDao(): PlaylistTrackDao
    abstract override fun updateDao(): UpdateDao
}