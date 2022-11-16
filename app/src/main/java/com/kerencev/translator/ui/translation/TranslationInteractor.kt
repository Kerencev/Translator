package com.kerencev.translator.ui.translation

import io.reactivex.rxjava3.core.Observable

interface TranslationInteractor<T : Any> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}