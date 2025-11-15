package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TechniqueDao {
    @Query("SELECT * FROM techniques WHERE novelId = :novelId ORDER BY name ASC")
    fun getTechniquesByNovel(novelId: Int): Flow<List<Technique>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(technique: Technique)

    @Update
    suspend fun update(technique: Technique)

    @Delete
    suspend fun delete(technique: Technique)
}