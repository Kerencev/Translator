package com.kerencev.translator.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kerencev.data.dto.DataModel
import com.kerencev.domain.HistoryRepository
import com.kerencev.domain.Repository
import kotlinx.coroutines.*

abstract class SearchViewModel : ViewModel() {

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
        private val interactor: Interactor<DataModel>,
        private val historyRepository: HistoryRepository
    ) : SearchViewModel() {

        override val liveData = MutableLiveData<SearchState>()

        override fun getData(word: String) {
            liveData.value = SearchState.Loading
            cancelJob()
            viewModelCoroutineScope.launch {
                withContext(Dispatchers.IO) {
                    val data = interactor.getData(word)
                    liveData.postValue(SearchState.Success(data))
                    historyRepository.saveHistory(data.first())
                }
            }
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

    suspend fun getData(word: String): List<T>

    class MainInteractor(
        private val repositoryRemote: Repository<List<DataModel>>
    ) : Interactor<DataModel> {

        override suspend fun getData(word: String): List<DataModel> {
            return repositoryRemote.getData(word)
        }
    }
}