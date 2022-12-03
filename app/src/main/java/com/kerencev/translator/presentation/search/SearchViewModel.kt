package com.kerencev.translator.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.domain.repository.Repository
import kotlinx.coroutines.*

abstract class SearchViewModel() : ViewModel() {

    abstract val liveData: MutableLiveData<SearchState>

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
    ) : SearchViewModel() {

        override val liveData = MutableLiveData<SearchState>()

        override fun getData(word: String) {
            liveData.value = SearchState.Loading
            cancelJob()
            viewModelCoroutineScope.launch { startInteractor(word) }

        }

        private suspend fun startInteractor(word: String) =
            withContext(Dispatchers.IO) {
                liveData.postValue(interactor.getData(word))
            }

        override fun handleError(error: Throwable) {
            liveData.postValue(SearchState.Error(error))
        }

        override fun onCleared() {
            liveData.value = SearchState.Success(null)
            super.onCleared()
        }
    }
}

interface Interactor<T : Any> {

    suspend fun getData(word: String): T

    class MainInteractor(
        private val repositoryRemote: Repository<List<DataModel>>
    ) : Interactor<SearchState> {

        override suspend fun getData(word: String): SearchState {
            return SearchState.Success(repositoryRemote.getData(word))
        }
    }
}