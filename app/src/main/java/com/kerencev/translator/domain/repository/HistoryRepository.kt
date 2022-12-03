package com.kerencev.translator.domain.repository

import com.kerencev.translator.data.local.DataBase
import com.kerencev.translator.presentation.details.DetailsModel

interface HistoryRepository {

    suspend fun getAllHistory(): List<DetailsModel>

    class Base(private val db: DataBase) : HistoryRepository {

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