package com.kerencev.translator.presenter

import com.kerencev.translator.model.data.AppState
import com.kerencev.translator.view.base.View

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}