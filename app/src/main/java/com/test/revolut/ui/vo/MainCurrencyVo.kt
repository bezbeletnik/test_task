package com.test.revolut.ui.vo

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes


data class MainCurrencyVo(
    @DrawableRes
    val image: Int,
    val currencyShortName: String,
    val currencyFullName: String,
    val amount: String
)