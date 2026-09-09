package com.mkiperszmid.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.mkiperszmid.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                val state = viewModel.state
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        CurrencyItem(
                            value = state.amountToConvert,
                            currency = state.currencyToConvert,
                            onValueChange = viewModel::onAmountToConvertChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(onClick = { viewModel.swapCurrencies() }, shape = CircleShape) {
                            Text(text = "swap")
                        }
                        CurrencyItem(
                            value = state.amountToReceive,
                            currency = state.currencyToReceive,
                            onValueChange = viewModel::onAmountToReceiveChange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        state.errorMessage?.let {
                            Text("Error! $it")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(
    value: String,
    currency: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(onClick = {}) {
            Text(currency)
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}