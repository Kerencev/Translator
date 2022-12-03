package com.kerencev.translator.domain.repository

import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.data.local.DataBase
import com.kerencev.translator.presentation.details.DetailsModel

interface HistoryRepository {

    suspend fun saveHistory(data: DataModel)
    suspend fun getAllHistory(): List<DetailsModel>

    class Base(private val db: DataBase) : HistoryRepository {

        override suspend fun saveHistory(data: DataModel) {
            db.historyDao().insert(data.convertToHistoryEntity())
        }

        override suspend fun getAllHistory(): List<DetailsModel> {
            return listOf(
                DetailsModel(
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
    }
}