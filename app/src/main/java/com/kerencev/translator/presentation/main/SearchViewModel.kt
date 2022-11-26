package com.kerencev.translator.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.translator.presentation.base.AppState
import kotlinx.coroutines.*

abstract class SearchViewModel<T : AppState>(
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

    abstract fun getData(word: String)

    abstract fun handleError(error: Throwable)

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }

    class Base(
        private val interactor: Interactor.MainInteractor
    ) : SearchViewModel<AppState>() {

        private val liveData: LiveData<AppState> = _liveData

        fun subscribe(): LiveData<AppState> {
            return liveData
        }

        override fun getData(word: String) {
            _liveData.value = AppState.Loading
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