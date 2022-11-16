package com.kerencev.translator.model.datasource

import com.kerencev.translator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}