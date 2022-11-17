package com.kerencev.translator.di.module

import com.kerencev.translator.di.NAME_LOCAL
import com.kerencev.translator.di.NAME_REMOTE
import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {
    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}