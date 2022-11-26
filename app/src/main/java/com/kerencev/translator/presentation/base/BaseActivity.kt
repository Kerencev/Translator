package com.kerencev.translator.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.kerencev.translator.data.dto.DataModel
import com.kerencev.translator.presentation.main.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

}

sealed class AppState {

    data class Success(val data: List<DataModel>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}

