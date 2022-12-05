package com.kerencev.domain

import com.kerencev.data.dto.DataModel
import com.kerencev.data.local.dao.HistoryDao
import com.kerencev.domain.model.DetailsModel
import com.kerencev.domain.utils.Converter

interface HistoryRepository {

    suspend fun saveHistory(data: DataModel)
    suspend fun getAllHistory(): List<DetailsModel>

    class Base(private val historyDao: HistoryDao) : HistoryRepository {

        override suspend fun saveHistory(data: DataModel) {
            historyDao.insert(data.convertToHistoryEntity())
        }

        override suspend fun getAllHistory(): List<DetailsModel> {
            return historyDao.getAll().map { historyEntity ->
                Converter.toDetailsModel(historyEntity)
            }
        }
    }
}