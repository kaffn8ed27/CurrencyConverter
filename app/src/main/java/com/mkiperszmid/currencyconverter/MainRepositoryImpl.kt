package com.mkiperszmid.currencyconverter

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.JsonException

class MainRepositoryImpl(
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

}