package com.kerencev.translator.presentation.search

import com.kerencev.translator.presentation.base.AppState

interface SearchFragment<T : AppState> {
    abstract fun renderData(appState: T)
}