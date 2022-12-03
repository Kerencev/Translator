package com.kerencev.translator.di

import androidx.room.Room
import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.data.local.DataBase
import com.kerencev.translator.data.remote.DataSource
import com.kerencev.translator.domain.repository.HistoryRepository
import com.kerencev.translator.domain.repository.Repository
import com.kerencev.translator.domain.repository.RepositoryImplementation
import com.kerencev.translator.presentation.history.HistoryViewModel
import com.kerencev.translator.presentation.search.Interactor
import com.kerencev.translator.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>> {
        RepositoryImplementation(DataSource.RetrofitImplementation())
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "history.db"
        ).build()
    }
    single<HistoryRepository> { HistoryRepository.Base(db = get()) }
}

val searchScreen = module {
    factory { Interactor.MainInteractor(repositoryRemote = get()) }
    viewModel<SearchViewModel> { SearchViewModel.Base(interactor = get()) }
}

val historyScreen = module {
    viewModel<HistoryViewModel> { HistoryViewModel.Base(repository = get()) }
}
