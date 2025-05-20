package com.layka.musicapphse.storage.localRepo.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithTracks(
    @Embedded val playlist: PlaylistEntity,

    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        entity = TrackEntity::class,
        associateBy = Junction(
            value = PlaylistTrack::class,
            parentColumn = "playlistId",
            entityColumn = "trackId"
        )
    )
    val tracks: List<TrackEntity>
)

@Entity(
    primaryKeys = ["trackId", "playlistId"]
)
data class PlaylistTrack(
    val trackId: Int,
    val playlistId: Int
)