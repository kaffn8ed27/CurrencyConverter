package com.mkiperszmid.currencyconverter.db

import com.mkiperszmid.currencyconverter.networkmodels.CurrenciesResponse

fun CurrenciesResponse.toEntity(): CurrencyEntity = CurrencyEntity(
    isoCode = isoCode,
    name = name,
    symbol = symbol
)