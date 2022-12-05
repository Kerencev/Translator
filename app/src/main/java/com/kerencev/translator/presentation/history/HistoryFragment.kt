package com.kerencev.translator.presentation.history

import com.kerencev.domain.model.DetailsModel

interface HistoryFragment {
    fun requestData()
    fun renderData(historyState: HistoryState)
    fun showSuccess(historyState: HistoryState.Success)
    fun showLoading()
    fun showError(throwable: Throwable)
}

sealed class HistoryState {
    data class Success(val data: List<DetailsModel>, val isSearchState: Boolean = false) :
        HistoryState()

    data class Error(val error: Throwable) : HistoryState()
    object Loading : HistoryState()
}