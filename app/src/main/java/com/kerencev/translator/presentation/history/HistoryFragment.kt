package com.kerencev.translator.presentation.history

import com.kerencev.translator.presentation.details.DetailsModel

interface HistoryFragment {
    fun requestData()
    fun renderData(historyState: HistoryState)
}

sealed class HistoryState {
    data class Success(val data: List<DetailsModel>) : HistoryState()
    data class Error(val error: Throwable) : HistoryState()
    object Loading : HistoryState()
}