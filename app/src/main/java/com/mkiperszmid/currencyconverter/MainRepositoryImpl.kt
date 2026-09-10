package com.mkiperszmid.currencyconverter

import com.mkiperszmid.currencyconverter.networkmodels.CurrenciesResponse
import com.mkiperszmid.currencyconverter.networkmodels.CurrencyExchangeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
): MainRepository {

    override suspend fun getCurrencyExchange(
        from: String,
        to: String
    ): Result<Double> {
        val response = httpClient.get("https://api.frankfurter.dev/v2/rate/$from/$to")
        val body = response.body<CurrencyExchangeResponse>()
        return try {
            Result.success(body.rate)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrencies(): Result<List<CurrenciesResponse>> {
        val response = httpClient.get("https://api.frankfurter.dev/v2/currencies")
        val body = response.body<List<CurrenciesResponse>>()
        return try {
            Result.success(body)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

}