package com.kerencev.domain

import com.kerencev.data.dto.DataModel

interface Repository<T : Any> {

    suspend fun getData(word: String): List<DataModel>
}