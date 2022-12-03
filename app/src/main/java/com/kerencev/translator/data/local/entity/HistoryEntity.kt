package com.kerencev.translator.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "word") val word: String?,
    @ColumnInfo(name = "transcription") val transcription: String?,
    @ColumnInfo(name = "translates") val translates: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?
)
