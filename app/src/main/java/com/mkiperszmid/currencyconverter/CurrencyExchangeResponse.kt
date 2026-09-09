package com.mkiperszmid.currencyconverter


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyExchangeResponse(
    @SerialName("base")
    val base: String,
    @SerialName("date")
    val date: String,
    @SerialName("quote")
    val quote: String,
    @SerialName("rate")
    val rate: Double
)