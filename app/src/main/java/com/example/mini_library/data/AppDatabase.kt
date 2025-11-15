package com.example.mini_library.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Novel::class,
        Arc::class,
        Character::class,
        Sect::class,
        Technique::class,
        CultivationRank::class
    ],
    version = 4, // Phiên bản hiện tại
    exportSchema = false // <--- QUAN TRỌNG: Phải là false để tránh lỗi "Schema not found"
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun novelDao(): NovelDao
    abstract fun arcDao(): ArcDao
    abstract fun characterDao(): CharacterDao
    abstract fun sectDao(): SectDao
    abstract fun techniqueDao(): TechniqueDao
    abstract fun cultivationDao(): CultivationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wikihub_database"
                )
                    // Dòng này cực quan trọng: Cho phép xóa sạch dữ liệu cũ nếu version thay đổi
                    // Giúp tránh lỗi crash khi bạn nâng cấp Database
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}