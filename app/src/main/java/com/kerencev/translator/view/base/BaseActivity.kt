package com.kerencev.translator.view.base

import androidx.appcompat.app.AppCompatActivity
import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.view.main.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

}