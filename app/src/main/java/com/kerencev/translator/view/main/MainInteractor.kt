package com.kerencev.translator.view.main

import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.presenter.Interactor

class MainInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}