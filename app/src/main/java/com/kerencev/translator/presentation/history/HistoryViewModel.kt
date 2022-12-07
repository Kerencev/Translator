package com.kerencev.translator.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.domain.HistoryRepository
import com.kerencev.domain.model.DetailsModel
import com.kerencev.translator.utils.Filter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class HistoryViewModel : ViewModel() {

    abstract val liveData: LiveData<HistoryState>
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    abstract fun getData()
    abstract fun getData(editable: CharSequence?)
    abstract fun getCacheData()
    protected abstract fun handleError(error: Throwable)

    class Base(
        private val repository: HistoryRepository,
        private val filter: Filter<DetailsModel>
    ) : HistoryViewModel() {

        override val liveData = MutableLiveData<HistoryState>()
        private var cacheData = ArrayList<DetailsModel>()

        override fun getData() {
            liveData.value = HistoryState.Loading
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val data = repository.getAllHistory()
                liveData.postValue(HistoryState.Success(data = data, isSearchState = false))
                cacheData.clear()
                cacheData.addAll(data)
            }
        }

        override fun getData(editable: CharSequence?) {
            if (editable.isNullOrEmpty()) {
                liveData.value = HistoryState.Success(data = cacheData, isSearchState = true)
                return
            }
            val filteredData = filter.getFilteredList(charSequence = editable, data = cacheData)
            liveData.value = HistoryState.Success(filteredData, isSearchState = true)
        }

        override fun getCacheData() {
            liveData.value = HistoryState.Success(data = cacheData, isSearchState = false)
        }

        override fun handleError(error: Throwable) {
            liveData.postValue(HistoryState.Error(error))
        }
    }
}