package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.networkmodels.CurrenciesResponse

interface MainRepository {

    suspend fun getCurrencyExchange(from: String, to: String): Result<Double>

    suspend fun getCurrencies(): Result<List<CurrenciesResponse>>
}