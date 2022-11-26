package com.kerencev.translator.domain.repository

import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.data.remote.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}