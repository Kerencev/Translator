package com.kerencev.translator.di

import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.datasource.RetrofitImplementation
import com.kerencev.translator.model.datasource.RoomDataBaseImplementation
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.model.repository.RepositoryImplementation
import com.kerencev.translator.view.main.BaseViewModel
import com.kerencev.translator.view.main.Interactor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {
    factory { Interactor.MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { BaseViewModel.MainViewModel(get()) }
}
