package com.example.stockapp.data.dao

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StocksEntity::class,],
    version = 1,
)
abstract class StockDataBase: RoomDatabase() {
    abstract val dao: StockDao

    companion object {
        @Volatile
        private var Instance: StockDataBase? = null

        fun getDatabase(context: Context): StockDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StockDataBase::class.java, "stocks.db")
                    .build().also { Instance = it }
            }
        }
    }
}