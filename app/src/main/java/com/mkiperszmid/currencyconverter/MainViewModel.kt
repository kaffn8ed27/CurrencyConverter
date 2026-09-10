package com.mkiperszmid.currencyconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepository,
    private val reducer: CurrenciesReducer
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        getCurrencies()
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

    fun onCurrencyToConvertChange(currency: String) {
        state = state.copy(currencyToConvert = currency)
        getCurrencyExchange()
    }

    fun onCurrencyToReceiveChange(currency: String) {
        state = state.copy(currencyToReceive = currency)
        getCurrencyExchange()
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

    private fun getCurrencies() {
        state = state.copy(errorMessage = null)
        viewModelScope.launch {
            val response = repo.getCurrencies()
            response.onSuccess {
                state = state.copy(currencies = reducer.reduceCurrenciesResponse(it))
            }
                .onFailure {
                    state = state.copy(
                        errorMessage = it.message,
                        currencies = emptyList()
                    )
                }
        }
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