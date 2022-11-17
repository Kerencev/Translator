package com.kerencev.translator.model.repository

import com.kerencev.translator.model.data.DataModel

interface Repository<T : Any> {

    suspend fun getData(word: String): List<DataModel>
}