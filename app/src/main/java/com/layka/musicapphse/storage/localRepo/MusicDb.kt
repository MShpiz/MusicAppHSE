package com.layka.musicapphse.storage.localRepo

import com.layka.musicapphse.storage.localRepo.dao.PlaylistDao
import com.layka.musicapphse.storage.localRepo.dao.PlaylistTrackDao
import com.layka.musicapphse.storage.localRepo.dao.TrackDao
import com.layka.musicapphse.storage.localRepo.entities.PlaylistWithTracks

interface MusicDb {
    fun trackDao(): TrackDao
    fun playlistDao(): PlaylistDao
    fun playlistTrackDao(): PlaylistTrackDao
}