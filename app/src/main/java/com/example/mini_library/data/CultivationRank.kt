package com.example.mini_library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cultivation_ranks")
data class CultivationRank(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val novelId: Int,
    val name: String,
    val order: Int,
    val description: String,
    val imageUri: String? = null // This MUST be nullable for migration to work
)