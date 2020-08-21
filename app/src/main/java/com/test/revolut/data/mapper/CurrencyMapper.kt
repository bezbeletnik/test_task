package com.test.revolut.data.mapper

import com.test.revolut.data.dto.CurrenciesDto
import com.test.revolut.domain.model.CurrencyCode
import com.test.revolut.domain.model.CurrencyRate
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {

    fun map(dto: CurrenciesDto): List<CurrencyRate> {
        if (dto.baseCurrency == null) {
            Timber.e("baseCurrency must be not null")
            return emptyList()
        }
        if (dto.rates == null) {
            Timber.e("rates must be not null")
            return emptyList()
        }

        val baseCurrency = mapCurrencyCode(dto.baseCurrency) ?: return emptyList()
        return dto.rates.mapNotNull {
            CurrencyRate(
                baseCurrency = baseCurrency,
                currency = mapCurrencyCode(it.key) ?: return@mapNotNull null,
                rate = it.value
            )
        }
    }

    private fun mapCurrencyCode(code: String): CurrencyCode? {
        return when (code.toUpperCase(Locale.ENGLISH)) {
            "EUR" -> CurrencyCode.EUR
            "USD" -> CurrencyCode.USD
            "RUR" -> CurrencyCode.RUR
            else -> null
        }
    }
}