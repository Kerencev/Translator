package com.kerencev.translator.view.base

import com.kerencev.translator.model.data.AppState

interface View {

    fun renderData(appState: AppState)

}