package com.example.mini_library

import android.app.Application
import com.example.mini_library.data.AppDatabase
import com.example.mini_library.data.WikiRepository

class WikiApplication : Application() {
    // Tạo database và repository một lần duy nhất khi app khởi động
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { WikiRepository(database.novelDao(), database.arcDao(), database.characterDao()) }
}