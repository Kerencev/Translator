package com.kerencev.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kerencev.data.local.entity.HistoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
) : Parcelable {

    fun convertToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            id = 0,
            word = text,
            transcription = this.meanings?.first()?.transcription,
            translates = getAllTranslates(),
            imageUrl = "https:${this.meanings?.first()?.imageUrl}"
        )
    }

    private fun getAllTranslates(): String {
        return StringBuilder().apply {
            meanings?.forEach { meaning ->
                meaning.translation?.translation?.let { it -> append("$it\n") }
            }
        }.toString()
    }
}