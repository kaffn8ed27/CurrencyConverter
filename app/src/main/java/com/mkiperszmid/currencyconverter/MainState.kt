package com.mkiperszmid.currencyconverter

import java.math.BigDecimal

data class MainState(
    val amountToConvert: String = "",
    val currencyToConvert: String = "USD",
    val amountToReceive: String = "",
    val currencyToReceive: String = "ARS",
    val rate: BigDecimal? = null,
    val errorMessage: String? = null
)