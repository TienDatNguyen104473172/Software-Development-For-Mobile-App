package com.example.mini_library // Đảm bảo đúng package của bạn

import android.app.Application
import com.example.mini_library.data.AppDatabase
import com.example.mini_library.data.WikiRepository

class WikiApplication : Application() {

    // 1. Tạo Database (Cái kho lớn)
    val database by lazy { AppDatabase.getDatabase(this) }

    // 2. Tạo Repository (Người quản lý kho)
    // LƯU Ý: Ở đây chúng ta phải đưa đủ 4 DAO vào bên trong ngoặc
    val repository by lazy {
        WikiRepository(
            database.novelDao(),
            database.arcDao(),
            database.characterDao(),
            database.sectDao(), // <-- ĐÂY LÀ CÁI MỚI THÊM VÀO
            database.techniqueDao(), // <-- Thêm dòng này
            database.cultivationDao() // <-- Thêm dòng này vào cuối
        )
    }
}