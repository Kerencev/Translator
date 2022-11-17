package com.kerencev.translator.model.datasource

interface DataSource<T : Any> {

    suspend fun getData(word: String): T
}