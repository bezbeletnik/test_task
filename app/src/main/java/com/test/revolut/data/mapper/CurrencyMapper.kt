package com.test.revolut.data.mapper

import com.test.revolut.data.dto.CurrenciesDto
import com.test.revolut.domain.model.CurrencyRate
import timber.log.Timber
import javax.inject.Inject

class CurrencyMapper @Inject constructor(
    private val currencyCodeMapper: CurrencyCodeMapper
) {

    fun map(dto: CurrenciesDto): List<CurrencyRate> {
        if (dto.baseCurrency == null) {
            Timber.e("baseCurrency must be not null")
            return emptyList()
        }
        if (dto.rates == null) {
            Timber.e("rates must be not null")
            return emptyList()
        }

        val baseCurrency = currencyCodeMapper.mapCurrencyCode(dto.baseCurrency) ?: return emptyList()
        return dto.rates.mapNotNull {
            CurrencyRate(
                baseCurrency = baseCurrency,
                currency = currencyCodeMapper.mapCurrencyCode(it.key) ?: return@mapNotNull null,
                rate = it.value
            )
        }
    }
}