package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.networkmodels.CurrenciesResponse
import javax.inject.Inject

class CurrenciesReducer @Inject constructor() {
    fun reduceCurrenciesResponse(response: List<CurrenciesResponse>): List<String> {
        val currencyStrings = mutableListOf<String>()
        response.forEach {
            currencyStrings.add(it.isoCode)
        }
        return currencyStrings
    }
}