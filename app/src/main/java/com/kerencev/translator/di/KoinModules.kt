package com.kerencev.translator.di

import androidx.room.Room
import com.kerencev.data.dto.DataModel
import com.kerencev.data.local.DataBase
import com.kerencev.data.remote.DataSource
import com.kerencev.domain.HistoryRepository
import com.kerencev.domain.Repository
import com.kerencev.domain.RepositoryImplementation
import com.kerencev.translator.presentation.history.HistoryViewModel
import com.kerencev.translator.presentation.search.Interactor
import com.kerencev.translator.presentation.search.SearchFragmentImpl
import com.kerencev.translator.presentation.search.SearchViewModel
import com.kerencev.translator.test.TestDep
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<DataModel>>> {
        RepositoryImplementation(DataSource.RetrofitImplementation())
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            DataBase::class.java,
            "history.db",
        )
            .fallbackToDestructiveMigration()
            .build()
            .historyDao()
    }
    single<HistoryRepository> { HistoryRepository.Base(historyDao = get()) }
}

val searchScreen = module {
    scope(named(SCOPE_A)) {
        scoped { TestDep() }
    }
    scope<SearchFragmentImpl> {
        scoped { TestDep() }
    }

    factory<Interactor<DataModel>> { Interactor.MainInteractor(repositoryRemote = get()) }
    viewModel<SearchViewModel> {
        SearchViewModel.Base(
            interactor = get(),
            historyRepository = get()
        )
    }
}

val historyScreen = module {
    viewModel<HistoryViewModel> { HistoryViewModel.Base(repository = get()) }
}

const val SCOPE_A = "SCOPE_A"
const val SCOPE_B = "SCOPE_B"
