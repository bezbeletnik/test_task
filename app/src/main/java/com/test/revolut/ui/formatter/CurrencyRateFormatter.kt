package com.test.revolut.ui.formatter

import com.test.revolut.data.mapper.CurrencyCodeMapper
import com.test.revolut.domain.model.CurrencyRate
import com.test.revolut.domain.usecase.GetCurrencyNameUseCase
import com.test.revolut.ui.vo.CurrencyRateVo
import timber.log.Timber
import javax.inject.Inject

class CurrencyRateFormatter @Inject constructor(
    private val codeMapper: CurrencyCodeMapper,
    private val iconFormatter: CurrencyIconFormatter,
    private val getCurrencyNameUseCase: GetCurrencyNameUseCase
) {

    fun format(
        currencyRates: List<CurrencyRate>,
        mainCurrencyAmount: Double
    ): List<CurrencyRateVo> {
        return currencyRates.map {
            val isoCode = codeMapper.mapToIsoCode(it.rateCurrencyCode)
            CurrencyRateVo(
                image = iconFormatter.getImage(it.rateCurrencyCode),
                currencyShortName = isoCode,
                currencyFullName = getCurrencyNameUseCase.execute(isoCode) ?: handleUnkownName(
                    isoCode
                ),
                rate = String.format("%.2f", it.rate * mainCurrencyAmount),
                currencyCode = it.rateCurrencyCode
            )
        }
    }

    private fun handleUnkownName(isoCode: String): String {
        Timber.e("full name not found for $isoCode")
        return ""
    }
}