package com.kerencev.translator.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kerencev.translator.di.ViewModelFactory
import com.kerencev.translator.di.ViewModelKey
import com.kerencev.translator.view.main.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel.MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: BaseViewModel.MainViewModel): ViewModel
}
