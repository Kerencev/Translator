package com.kerencev.translator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.translator.presentation.base.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>(
    protected val _liveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData(word: String, isOnline: Boolean)

    abstract fun handleError(error: Throwable)

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }

    class MainViewModel(
        private val interactor: Interactor.MainInteractor
    ) : BaseViewModel<AppState>() {

        private val liveData: LiveData<AppState> = _liveData

        fun subscribe(): LiveData<AppState> {
            return liveData
        }

        override fun getData(word: String, isOnline: Boolean) {
            _liveData.value = AppState.Loading(null)
            cancelJob()
            viewModelCoroutineScope.launch { startInteractor(word) }

        }

        private suspend fun startInteractor(word: String) =
            withContext(Dispatchers.IO) {
                _liveData.postValue(interactor.getData(word))
            }

        override fun handleError(error: Throwable) {
            _liveData.postValue(AppState.Error(error))
        }

        override fun onCleared() {
            _liveData.value = AppState.Success(null)
            super.onCleared()
        }
    }
}