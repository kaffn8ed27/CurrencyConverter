package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.db.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getCurrencyExchange(from: String, to: String): Result<Double>

    suspend fun getCurrencies(): Flow<List<CurrencyEntity>>

    suspend fun refreshCurrencies(): Result<Unit>
}