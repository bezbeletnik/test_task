package com.test.revolut.domain.model

import com.test.revolut.data.mapper.CurrencyCode

data class CurrencyRate(
    val baseCurrency: CurrencyCode,
    val currency: CurrencyCode,
    val rate: Float
)