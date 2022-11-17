package com.kerencev.translator.di.module

import com.kerencev.translator.di.NAME_LOCAL
import com.kerencev.translator.di.NAME_REMOTE
import com.kerencev.translator.model.data.DataModel
import com.kerencev.translator.model.datasource.DataSource
import com.kerencev.translator.model.datasource.RetrofitImplementation
import com.kerencev.translator.model.datasource.RoomDataBaseImplementation
import com.kerencev.translator.model.repository.Repository
import com.kerencev.translator.model.repository.RepositoryImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote:
        DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(NAME_LOCAL) dataSourceLocal:
        DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> =
        RoomDataBaseImplementation()
}
