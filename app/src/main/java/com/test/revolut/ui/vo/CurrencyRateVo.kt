package com.test.revolut.ui.vo


data class CurrencyRateVo(
    val imageUrl: String,
    val currencyShortName: String,
    val currencyFullName: String,
    val rate: String
)