package com.example.mini_library.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Khai báo đây là Database, bao gồm các bảng nào, version bao nhiêu
@Database(entities = [Novel::class, Arc::class, Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // 2. Khai báo các DAO để truy cập dữ liệu
    abstract fun novelDao(): NovelDao
    abstract fun arcDao(): ArcDao
    abstract fun characterDao(): CharacterDao

    // 3. Companion object để tạo instance duy nhất (Singleton pattern)
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Nếu INSTANCE đã có thì trả về, nếu chưa thì tạo mới
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wikihub_database" // Tên file database trên điện thoại
                )
                    .fallbackToDestructiveMigration() // Nếu đổi version mà chưa biết migrate thì xóa cũ tạo mới (chỉ dùng lúc dev)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}