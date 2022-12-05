package com.kerencev.translator.utils

import com.kerencev.data.dto.DataModel
import com.kerencev.data.dto.Meanings
import com.kerencev.domain.model.DetailsModel

object Converter {

    fun convertToDetailsModel(dataModel: DataModel): DetailsModel {
        return DetailsModel(
            id = dataModel.id ?: "-1",
            word = dataModel.text,
            transcription = dataModel.meanings?.first()?.transcription,
            translates = getAllTranslates(dataModel.meanings),
            imageUrl = "https:${dataModel.meanings?.first()?.imageUrl}"
        )
    }

    private fun getAllTranslates(meanings: List<Meanings>?): String {
        return StringBuilder().apply {
            meanings?.forEach { meaning ->
                meaning.translation?.translation?.let { it -> append("$it\n") }
            }
        }.toString()
    }
}