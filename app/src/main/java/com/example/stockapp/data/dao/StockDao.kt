package com.example.stockapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//framework Room
//BD: SQLite

@Dao
interface StockDao {

    @Query("SELECT * FROM StocksEntity")
    suspend fun getAllStocks(): List<StocksEntity>

    @Query("SELECT * FROM StocksEntity WHERE secId = :id")
    fun getStockById(id: String): Flow<StocksEntity>

    @Update
    suspend fun update(item: StocksEntity)

    @Query("SELECT * FROM StocksEntity WHERE amount > 0")
    fun getAllMyStocks(): Flow<List<StocksEntity>>
}