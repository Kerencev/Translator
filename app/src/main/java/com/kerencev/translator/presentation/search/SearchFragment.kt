package com.kerencev.translator.presentation.search

import com.kerencev.translator.data.dto.DataModel

interface SearchFragment<T : SearchState> {
    fun renderData(appState: T)
}

sealed class SearchState {

    data class Success(val data: List<DataModel>?) : SearchState()
    data class Error(val error: Throwable) : SearchState()
    object Loading : SearchState()
}