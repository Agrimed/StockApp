package com.example.stockapp.data.model


data class Stock(
    val secId: String,
    val name: String,
    val curPrice: Double,
    val amount: Int
)