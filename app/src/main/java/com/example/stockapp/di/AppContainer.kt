package com.example.stockapp.di

import android.content.Context
import com.example.stockapp.data.dao.StockDataBase
import com.example.stockapp.data.repository.StockRepository

class LocalAppContainer(context: Context){

    val stockRepository: StockRepository by lazy {
        StockRepository(
            StockDataBase.getDatabase(context).dao
        )
    }

}