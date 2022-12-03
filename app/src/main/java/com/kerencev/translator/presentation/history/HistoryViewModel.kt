package com.kerencev.translator.presentation.history

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerencev.translator.domain.repository.HistoryRepository
import com.kerencev.translator.presentation.details.DetailsModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class HistoryViewModel : ViewModel() {

    abstract val liveData: LiveData<HistoryState>
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    abstract fun getData()
    abstract fun getData(editable: Editable?)
    abstract fun handleError(error: Throwable)

    class Base(private val repository: HistoryRepository) : HistoryViewModel() {

        override val liveData = MutableLiveData<HistoryState>()
        private var cacheData = ArrayList<DetailsModel>()

        override fun getData() {
            liveData.value = HistoryState.Loading
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val data = repository.getAllHistory()
                liveData.postValue(HistoryState.Success(data = data))
                cacheData.addAll(data)
            }
        }

        override fun getData(editable: Editable?) {
            if (editable.isNullOrEmpty()) {
                liveData.value = HistoryState.Success(data = cacheData)
                return
            }
            liveData.value = HistoryState.Success(filter(editable))
        }

        override fun handleError(error: Throwable) {
            liveData.value = HistoryState.Error(error)
        }

        private fun filter(editable: Editable): List<DetailsModel> {
            val result = mutableListOf<DetailsModel>()
            cacheData.forEach { detailsModel ->
                val word = detailsModel.word ?: ""
                val translate =
                    detailsModel.translates.substring(0, detailsModel.translates.indexOf('\n'))
                if (word.contains(editable, ignoreCase = true) || translate.contains(
                        editable,
                        ignoreCase = true
                    )
                ) {
                    result.add(detailsModel)
                }
            }
            return result
        }
    }
}