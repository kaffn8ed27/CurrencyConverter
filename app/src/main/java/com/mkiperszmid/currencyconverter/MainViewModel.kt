package com.mkiperszmid.currencyconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.math.BigDecimal

class MainViewModel(
    private val repo: MainRepository = MainRepositoryImpl(
        httpClient = HttpClient(engineFactory = Android) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    )
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        getCurrencyExchange()
    }

    fun onAmountToConvertChange(value: String) {
        val cleanedValue = cleanCurrencyString(value)
        state = state.copy(
            amountToConvert = cleanedValue
        )
        if (value.isBlank() || state.rate == null) return
        val newValue = BigDecimal(cleanedValue).multiply(state.rate).toPlainString()
        state = state.copy(
            amountToReceive = newValue
        )
    }

    fun onAmountToReceiveChange(value: String) {
        state = state.copy(amountToReceive = value)
    }

    fun swapCurrencies() {
        val currencyTemp = state.currencyToConvert
        val amountTemp = state.amountToConvert

        state = state.copy(
            amountToConvert = state.amountToReceive,
            currencyToConvert = state.currencyToReceive,
            amountToReceive = amountTemp,
            currencyToReceive = currencyTemp
        )
        getCurrencyExchange()
    }

    private fun getCurrencyExchange() {
        state = state.copy(errorMessage = null)
        viewModelScope.launch {
            repo.getCurrencyExchange(from = state.currencyToConvert, to = state.currencyToReceive)
                .onSuccess {
                    state = state.copy(
                        rate = it.toBigDecimal()
                      )
                }.onFailure {
                    state = state.copy(
                        errorMessage = it.message,
                        rate = null
                    )
                }
        }
    }

    private fun cleanCurrencyString(input: String): String = input.replace(Regex("[^\\d.]"), "")
}