package com.kerencev.translator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.translator.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class HistoryViewModel : ViewModel() {

    abstract val liveData: LiveData<HistoryState>
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    abstract fun getData()
    abstract fun handleError(error: Throwable)

    class Base(private val repository: HistoryRepository) : HistoryViewModel() {

        override val liveData = MutableLiveData<HistoryState>()

        override fun getData() {
            liveData.value = HistoryState.Loading
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val data = repository.getAllHistory()
                liveData.postValue(HistoryState.Success(data = data))
            }
        }

        override fun handleError(error: Throwable) {
            liveData.value = HistoryState.Error(error)
        }
    }
}