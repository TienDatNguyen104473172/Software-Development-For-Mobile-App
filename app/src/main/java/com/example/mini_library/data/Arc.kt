package com.example.mini_library.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "arcs",
    foreignKeys = [
        ForeignKey(
            entity = Novel::class,
            parentColumns = ["id"],
            childColumns = ["novelId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["novelId"])]
)
data class Arc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val novelId: Int,
    val title: String,
    val order: Int,
    val summary: String
)