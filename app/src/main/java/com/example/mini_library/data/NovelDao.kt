package com.example.mini_library.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NovelDao {
    // Lấy tất cả truyện, sắp xếp theo tên
    @Query("SELECT * FROM novels ORDER BY title ASC")
    fun getAllNovels(): Flow<List<Novel>>

    // Lấy một truyện cụ thể theo ID
    @Query("SELECT * FROM novels WHERE id = :id")
    fun getNovel(id: Int): Flow<Novel>

    // Thêm truyện mới (nếu trùng ID thì ghi đè)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(novel: Novel)

    // Cập nhật thông tin truyện
    @Update
    suspend fun update(novel: Novel)

    // Xóa một truyện
    @Delete
    suspend fun delete(novel: Novel)
}