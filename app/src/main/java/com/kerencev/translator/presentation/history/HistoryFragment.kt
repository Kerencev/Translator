package com.kerencev.translator.presentation.history

import com.kerencev.translator.presentation.details.DetailsModel

interface HistoryFragment {
    fun requestData()
    fun renderData(historyState: HistoryState)
    fun showSuccess(data: List<DetailsModel>)
    fun showLoading()
    fun showError(throwable: Throwable)
}

sealed class HistoryState {
    data class Success(val data: List<DetailsModel>) : HistoryState()
    data class Error(val error: Throwable) : HistoryState()
    object Loading : HistoryState()
}