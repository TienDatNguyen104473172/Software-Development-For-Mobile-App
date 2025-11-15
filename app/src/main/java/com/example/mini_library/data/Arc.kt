package com.example.mini_library.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arcs")
data class Arc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val novelId: Int,
    val title: String,
    val order: Int,
    val description: String,
    val imageUri: String? = null // This MUST be nullable for migration to work
)