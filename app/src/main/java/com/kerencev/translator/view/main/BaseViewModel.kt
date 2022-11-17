package com.kerencev.translator.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

abstract class BaseViewModel<T : AppState>(
    protected val liveData: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    class MainViewModel @Inject constructor(
        private val interactor: MainInteractor
    ) : BaseViewModel<AppState>() {

        private var appState: AppState? = null

        fun subscribe(): LiveData<AppState> {
            return liveData
        }

        override fun getData(word: String, isOnline: Boolean) {
            compositeDisposable.add(
                interactor.getData(word, isOnline)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(doOnSubscribe())
                    .subscribeWith(getObserver())
            )
        }

        private fun doOnSubscribe(): (Disposable) -> Unit =
            { liveData.value = AppState.Loading(null) }


        private fun getObserver(): DisposableObserver<AppState> {
            return object : DisposableObserver<AppState>() {
                override fun onNext(state: AppState) {
                    appState = state
                    liveData.value = appState
                }

                override fun onError(e: Throwable) {
                    liveData.value = AppState.Error(e)
                }

                override fun onComplete() = Unit
            }
        }
    }
}