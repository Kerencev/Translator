package com.kerencev.translator.data

import io.reactivex.rxjava3.core.Observable

interface DataSource<T : Any> {
    fun getData(word: String): Observable<T>
}
