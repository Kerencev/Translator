package com.kerencev.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "word") val word: String?,
    @ColumnInfo(name = "transcription") val transcription: String?,
    @ColumnInfo(name = "translates") val translates: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?
)
