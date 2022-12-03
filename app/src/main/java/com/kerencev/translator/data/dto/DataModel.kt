package com.kerencev.translator.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kerencev.translator.data.local.entity.HistoryEntity
import com.kerencev.translator.presentation.details.DetailsModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
) : Parcelable {

    fun convertToDetailsModel(): DetailsModel {
        return DetailsModel(
            word = this.text,
            transcription = this.meanings?.first()?.transcription,
            translates = getAllTranslates(),
            imageUrl = "https:${this.meanings?.first()?.imageUrl}"
        )
    }

    fun convertToHistoryEntity(): HistoryEntity {
        return HistoryEntity(
            id = id ?: "-1",
            word = text,
            transcription = this.meanings?.first()?.transcription,
            translates = getAllTranslates(),
            imageUrl = "https:${this.meanings?.first()?.imageUrl}"
        )
    }

    private fun getAllTranslates(): String {
        return StringBuilder().apply {
            this@DataModel.meanings?.forEach { meaning ->
                meaning.translation?.translation?.let { it -> append("$it\n") }
            }
        }.toString()
    }
}