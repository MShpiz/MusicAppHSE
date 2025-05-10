package com.layka.musicapphse.storage.localRepo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity (
    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="artist")
    val artist: String,

    @ColumnInfo(name="duration")
    val duration: Int,

    @ColumnInfo(name="uri")
    val uri: String,

    @ColumnInfo(name="cover")
    val albumCover: String?,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="trackId")
    val trackId:Int = 0
)


