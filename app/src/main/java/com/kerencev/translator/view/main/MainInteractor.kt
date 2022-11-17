package com.kerencev.translator.view.main

import com.kerencev.translator.di.NAME_LOCAL
import com.kerencev.translator.di.NAME_REMOTE
import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.presenter.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}