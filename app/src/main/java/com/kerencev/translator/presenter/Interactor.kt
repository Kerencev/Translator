package com.kerencev.translator.presenter

interface Interactor<T : Any> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}