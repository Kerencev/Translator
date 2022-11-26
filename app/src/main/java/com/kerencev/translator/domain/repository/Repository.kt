package com.kerencev.translator.domain.repository

import com.kerencev.translator.data.dto.DataModel

interface Repository<T : Any> {

    suspend fun getData(word: String): List<DataModel>
}