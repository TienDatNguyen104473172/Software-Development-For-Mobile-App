package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ArcDao {
    // Lấy tất cả Arc của một truyện cụ thể, sắp xếp theo thứ tự
    @Query("SELECT * FROM arcs WHERE novelId = :novelId ORDER BY `order` ASC")
    fun getArcsByNovel(novelId: Int): Flow<List<Arc>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(arc: Arc)

    @Update
    suspend fun update(arc: Arc)

    @Delete
    suspend fun delete(arc: Arc)
}