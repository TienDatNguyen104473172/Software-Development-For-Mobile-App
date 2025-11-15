package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CultivationDao {
    // Lấy danh sách cảnh giới, SẮP XẾP THEO THỨ TỰ (Order)
    @Query("SELECT * FROM cultivation_ranks WHERE novelId = :novelId ORDER BY `order` ASC")
    fun getRanksByNovel(novelId: Int): Flow<List<CultivationRank>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rank: CultivationRank)

    @Update
    suspend fun update(rank: CultivationRank)

    @Delete
    suspend fun delete(rank: CultivationRank)
}