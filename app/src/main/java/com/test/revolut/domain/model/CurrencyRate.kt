package com.test.revolut.domain.model

data class CurrencyRate(
    val baseCurrency: CurrencyCode,
    val currency: CurrencyCode,
    val rate: Float
)

enum class CurrencyCode {
    EUR, USD, RUR
}