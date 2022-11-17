package com.kerencev.translator.model.datasource

import com.kerencev.translator.model.data.DataModel

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented")
    }
}