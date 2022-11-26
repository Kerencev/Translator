package com.kerencev.translator.presentation.main

import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.domain.repository.Repository
import com.kerencev.translator.presentation.base.AppState


interface Interactor<T : Any> {

    suspend fun getData(word: String): T

    class MainInteractor(
        private val repositoryRemote: Repository<List<DataModel>>
    ) : Interactor<AppState> {

        override suspend fun getData(word: String): AppState {
            return AppState.Success(repositoryRemote.getData(word))
        }
    }
}
