package com.test.revolut.ui.vo

import androidx.annotation.DrawableRes
import com.test.revolut.data.mapper.CurrencyCode


data class CurrencyRateVo(
    @DrawableRes
    val image: Int,
    val currencyShortName: String,
    val currencyFullName: String,
    val rate: String,
    val rateValue: Double,
    val currencyCode: CurrencyCode
)