package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    // Lấy tất cả nhân vật của một truyện
    @Query("SELECT * FROM characters WHERE novelId = :novelId ORDER BY name ASC")
    fun getCharactersByNovel(novelId: Int): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Update
    suspend fun update(character: Character)

    @Delete
    suspend fun delete(character: Character)
}