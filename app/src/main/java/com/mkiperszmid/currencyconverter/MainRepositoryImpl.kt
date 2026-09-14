package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.db.CurrencyDao
import com.mkiperszmid.currencyconverter.db.CurrencyEntity
import com.mkiperszmid.currencyconverter.db.toEntity
import com.mkiperszmid.currencyconverter.networkmodels.CurrenciesResponse
import com.mkiperszmid.currencyconverter.networkmodels.CurrencyExchangeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val currencyDao: CurrencyDao
) : MainRepository {

    override suspend fun getCurrencyExchange(
        from: String,
        to: String
    ): Result<Double> {
        return try {
            val response = httpClient.get("https://api.frankfurter.dev/v2/rate/$from/$to")
            val body = response.body<CurrencyExchangeResponse>()
            Result.success(body.rate)
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrencies(): Flow<List<CurrencyEntity>> = currencyDao.getAll()

    override suspend fun refreshCurrencies(): Result<Unit> {
        return try {
            val response = httpClient.get("https://api.frankfurter.dev/v2/currencies")
            val body = response.body<List<CurrenciesResponse>>()
            currencyDao.insertAll(body.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}