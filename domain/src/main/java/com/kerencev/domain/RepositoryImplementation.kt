package com.kerencev.domain

import com.kerencev.data.dto.DataModel
import com.kerencev.data.remote.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}