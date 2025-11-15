package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SectDao {
    @Query("SELECT * FROM sects WHERE novelId = :novelId ORDER BY name ASC")
    fun getSectsByNovel(novelId: Int): Flow<List<Sect>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sect: Sect)

    @Update
    suspend fun update(sect: Sect)

    @Delete
    suspend fun delete(sect: Sect)
}