package com.test.revolut.domain.model

import com.test.revolut.data.mapper.CurrencyCode

data class CurrencyRate(
    val baseCurrencyCode: CurrencyCode,
    val rateCurrencyCode: CurrencyCode,
    val rate: Float
)