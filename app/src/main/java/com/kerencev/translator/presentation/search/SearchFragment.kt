package com.kerencev.translator.presentation.search

import com.kerencev.data.dto.DataModel

interface SearchFragment<T : SearchState> {
    fun renderData(appState: T)
    fun showError(error: String?)
    fun showSuccess()
    fun showLoading()
}

sealed class SearchState {
    data class Success(val data: List<DataModel>?) : SearchState()
    data class Error(val error: Throwable) : SearchState()
    object Loading : SearchState()
}