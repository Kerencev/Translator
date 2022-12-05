package com.kerencev.domain.utils

import com.kerencev.data.local.entity.HistoryEntity
import com.kerencev.domain.model.DetailsModel

object Converter {
    fun toDetailsModel(historyEntity: HistoryEntity): DetailsModel {
        return DetailsModel(
            id = historyEntity.id.toString(),
            word = historyEntity.word,
            transcription = historyEntity.transcription,
            translates = historyEntity.translates,
            imageUrl = historyEntity.imageUrl
        )
    }
}