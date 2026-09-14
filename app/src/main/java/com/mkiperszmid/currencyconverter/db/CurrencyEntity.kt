package com.mkiperszmid.currencyconverter.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey val isoCode: String,
    val name: String,
    val symbol: String
)