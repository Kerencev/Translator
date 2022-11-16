package com.kerencev.translator.view.main

import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.presenter.Interactor
import io.reactivex.rxjava3.core.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}