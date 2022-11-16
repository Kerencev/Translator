package com.kerencev.translator.ui.translation

import com.kerencev.translator.data.DataModel

sealed class TranslationState {
    data class Success(val data: List<DataModel>?) : TranslationState()
    data class Error(val error: Throwable) : TranslationState()
    data class Loading(val progress: Int?) : TranslationState()
}
