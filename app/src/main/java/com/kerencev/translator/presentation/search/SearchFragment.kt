package com.kerencev.translator.presentation.search

import androidx.fragment.app.Fragment
import com.kerencev.translator.presentation.details.DetailsModel

interface SearchFragment<T : SearchState> {
    fun renderData(appState: T)
    fun showError(error: String?)
    fun showSuccess()
    fun showLoading()
    fun navigateToDetailsFragment(fragment: Fragment, data: DetailsModel)
}

sealed class SearchState {
    data class Success(val data: List<com.kerencev.translator.data.dto.DataModel>?) : SearchState()
    data class Error(val error: Throwable) : SearchState()
    object Loading : SearchState()
}