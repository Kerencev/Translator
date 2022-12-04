package com.kerencev.translator.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kerencev.translator.presentation.details.DetailsModel

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "word") val word: String?,
    @ColumnInfo(name = "transcription") val transcription: String?,
    @ColumnInfo(name = "translates") val translates: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?
) {
    fun toDetailsModel(): DetailsModel {
        return DetailsModel(
            id = id.toString(),
            word = word,
            transcription = transcription,
            translates = translates,
            imageUrl = imageUrl
        )
    }
}
