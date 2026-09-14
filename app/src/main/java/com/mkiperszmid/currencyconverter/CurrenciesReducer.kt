package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.db.CurrencyEntity
import javax.inject.Inject

class CurrenciesReducer @Inject constructor() {
    fun reduceCurrenciesResponse(currencies: List<CurrencyEntity>): List<String> =
        currencies.map { it.isoCode }
}