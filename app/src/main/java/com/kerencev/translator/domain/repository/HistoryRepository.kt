package com.kerencev.translator.domain.repository

import com.kerencev.translator.presentation.details.DetailsModel

interface HistoryRepository {

    suspend fun getAllHistory(): List<DetailsModel>

    class Base : HistoryRepository {

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