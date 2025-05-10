package com.layka.musicapphse.storage.localRepo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity (
    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="description")
    val description: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="playlistId")
    val playlistId:Int = 0
)