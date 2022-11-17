package com.kerencev.translator.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.model.datasource.DataSourceLocal
import com.kerencev.translator.model.datasource.DataSourceRemote
import com.kerencev.translator.model.repository.RepositoryImplementation
import com.kerencev.translator.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver

abstract class BaseViewModel<T : AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    open fun getData(word: String, isOnline: Boolean): LiveData<T> = liveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    class MainViewModel(
        private val interactor: MainInteractor = MainInteractor(
            RepositoryImplementation(DataSourceRemote()),
            RepositoryImplementation(DataSourceLocal())
        )
    ) : BaseViewModel<AppState>() {
        private var appState: AppState? = null

        override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
            compositeDisposable.add(
                interactor.getData(word, isOnline)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe {
                        liveData.value =
                            AppState.Loading(null)
                    }
                    .subscribeWith(getObserver())
            )
            return super.getData(word, isOnline)
        }

        private fun getObserver(): DisposableObserver<AppState> {
            return object : DisposableObserver<AppState>() {
                override fun onNext(state: AppState) {
                    appState = state
                    liveData.value = state
                }

                override fun onError(e: Throwable) {
                    liveData.value = AppState.Error(e)
                }

                override fun onComplete() {
                }
            }
        }
    }
}