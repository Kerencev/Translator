package com.kerencev.translator.ui.translation

interface TranslationPresenter<T : TranslationState, V : TranslationView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}
