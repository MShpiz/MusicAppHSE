package com.layka.musicapphse.storage.localRepo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.layka.musicapphse.storage.localRepo.entities.UpdateEntity

@Dao
interface UpdateDao {
    @Insert
    suspend fun insertUpdate(update: UpdateEntity)

    @Delete
    suspend fun deleteUpdate(update: UpdateEntity)

    @Update
    suspend fun updateUpdate(update: UpdateEntity)

    @Transaction
    @Query("SELECT * FROM updates WHERE updated = 0")
    suspend fun getAllNotUpdated(): List<UpdateEntity>?
}