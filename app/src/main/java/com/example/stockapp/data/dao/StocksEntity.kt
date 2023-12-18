package com.example.stockapp.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stockapp.data.model.Stock

@Entity
data class StocksEntity(
    @PrimaryKey
    val secId: String,
    val name: String,
    val curPrice: Double,
    val amount: Int = 0
)

fun StocksEntity.toStock() = Stock(secId, name, curPrice, amount) //expand to show on screen