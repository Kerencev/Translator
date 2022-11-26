package com.kerencev.translator.di

import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.data.remote.DataSource
import com.kerencev.translator.domain.repository.Repository
import com.kerencev.translator.domain.repository.RepositoryImplementation
import com.kerencev.translator.presentation.main.Interactor
import com.kerencev.translator.presentation.main.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>> {
        RepositoryImplementation(DataSource.RetrofitImplementation())
    }
}

val mainScreen = module {
    factory { Interactor.MainInteractor(repositoryRemote = get()) }

    viewModel { SearchViewModel.Base(interactor = get()) }
}
