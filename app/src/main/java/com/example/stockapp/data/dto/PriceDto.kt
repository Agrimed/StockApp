package com.example.stockapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceDto(
    @SerialName("currentprices")
    val currentPrices: CurrentPriceDto
)

@Serializable
data class CurrentPriceDto (
    val data: List<StockPriceDto>
)

@Serializable
data class StockPriceDto (
    val tradeDate: String,
    val secId: String,
    val currentPrice: Double
)