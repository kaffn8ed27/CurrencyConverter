package com.mkiperszmid.currencyconverter.networkmodels


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesResponse(
    @SerialName("iso_code")
    val isoCode: String,
    @SerialName("iso_numeric")
    val isoNumeric: String,
    @SerialName("name")
    val name: String,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("end_date")
    val endDate: String
)