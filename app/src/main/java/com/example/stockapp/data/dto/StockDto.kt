package com.example.stockapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockDto(
    @SerialName("quotedsecurities")
    val quotedSecurities: QuotedSecuritiesDto
)

@Serializable
data class QuotedSecuritiesDto (
    val data: List<StockNameDto>
)

@Serializable
data class StockNameDto (
    val name: String,
    val secId: String,
)