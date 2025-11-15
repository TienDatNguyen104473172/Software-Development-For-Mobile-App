package com.example.mini_library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "novels")
data class Novel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val coverUrl: String, // Keep for remote images
    val summary: String,
    val coverImageUri: String? // This MUST be nullable for migration to work
)