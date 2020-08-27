package com.test.revolut.ui.vo

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes


data class CurrencyRateVo(
    @DrawableRes
    val image: Int,
    val currencyShortName: String,
    val currencyFullName: String,
    val rate: String
)