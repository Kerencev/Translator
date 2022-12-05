package com.kerencev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kerencev.data.local.dao.HistoryDao
import com.kerencev.data.local.entity.HistoryEntity

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 2
)
abstract class DataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
