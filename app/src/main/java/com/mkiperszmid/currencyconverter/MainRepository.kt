package com.mkiperszmid.currencyconverter

interface MainRepository {

    suspend fun getCurrencyExchange(from: String, to: String): Result<Double>
}