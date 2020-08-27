package com.test.revolut.data.mapper

import java.util.Locale
import javax.inject.Inject

class CurrencyCodeMapper @Inject constructor() {

    private val currencies = lazy {
        mapOf(
            "EUR" to CurrencyCode.EUR,
            "AUD" to CurrencyCode.AUD,
            "BGN" to CurrencyCode.BGN,
            "BRL" to CurrencyCode.BRL,
            "CAD" to CurrencyCode.CAD,
            "CHF" to CurrencyCode.CHF,
            "CNY" to CurrencyCode.CNY,
            "CZK" to CurrencyCode.CZK,
            "DKK" to CurrencyCode.DKK,
            "GBP" to CurrencyCode.GBP,
            "HKD" to CurrencyCode.HKD,
            "HRK" to CurrencyCode.HRK,
            "HUF" to CurrencyCode.HUF,
            "IDR" to CurrencyCode.IDR,
            "ILS" to CurrencyCode.ILS,
            "INR" to CurrencyCode.INR,
            "ISK" to CurrencyCode.ISK,
            "JPY" to CurrencyCode.JPY,
            "KRW" to CurrencyCode.KRW,
            "MXN" to CurrencyCode.MXN,
            "MYR" to CurrencyCode.MYR,
            "NOK" to CurrencyCode.NOK,
            "NZD" to CurrencyCode.NZD,
            "PHP" to CurrencyCode.PHP,
            "PLN" to CurrencyCode.PLN,
            "RON" to CurrencyCode.RON,
            "RUB" to CurrencyCode.RUB,
            "SEK" to CurrencyCode.SEK,
            "SGD" to CurrencyCode.SGD,
            "THB" to CurrencyCode.THB,
            "USD" to CurrencyCode.USD,
            "ZAR" to CurrencyCode.ZAR
        )
    }

    fun mapCurrencyCode(code: String): CurrencyCode? {
        return currencies.value[code.toUpperCase(Locale.ENGLISH)]
    }

    fun mapToString(code: CurrencyCode): String {
        return currencies.value.entries.first { it.value == code }.key
    }
}

enum class CurrencyCode {
    EUR, AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, GBP, HKD, HRK, HUF, IDR, ILS, INR,
    ISK, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, USD, ZAR
}