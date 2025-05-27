package com.layka.musicapphse.storage.localRepo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "updates", primaryKeys = ["updateType", "mainId", "secondaryId"])
data class UpdateEntity(
    @ColumnInfo(name = "updated")
    val updated: Boolean,

    @ColumnInfo(name = "updateType")
    val updateType: UpdateType,

    @ColumnInfo(name = "mainId")
    val mainId: Int,

    @ColumnInfo(name = "secondaryId")
    val secondaryId: Int = -1,
)

enum class UpdateType(type: String) {
    UPDATE_PLAYLIST("updatePlaylist"),
    ADD_TRACK_TO_PLAYLIST("add track to playlist"),
    DELETE_TRACK_FROM_PLAYLIST("delete track from playlist");
}

