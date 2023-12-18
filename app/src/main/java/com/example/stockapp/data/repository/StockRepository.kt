package com.example.stockapp.data.repository

import com.example.stockapp.data.dao.StockDao
import com.example.stockapp.data.dao.StocksEntity

class StockRepository(val stockDB: StockDao) {
    suspend fun getAllStocks() = stockDB.getAllStocks()

    fun getStockById(id: String) = stockDB.getStockById(id)

    suspend fun update(stock: StocksEntity) = stockDB.update(stock)

    fun getAllMyStocks() = stockDB.getAllMyStocks()
}