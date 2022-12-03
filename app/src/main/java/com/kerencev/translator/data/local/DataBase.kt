package com.kerencev.translator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kerencev.translator.data.local.dao.HistoryDao
import com.kerencev.translator.data.local.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1
)
abstract class DataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}